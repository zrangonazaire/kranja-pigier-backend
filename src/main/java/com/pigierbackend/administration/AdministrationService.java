package com.pigierbackend.administration;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministrationService {

    private final AdministrationRepository repository;
    private final DelibererRepository delibererRepository;

    /** IMPORT EXCEL */
    public void importExcel(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide");
        }

        try (InputStream is = file.getInputStream()) {

            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rows = sheet.iterator();

            if (!rows.hasNext()) {
                throw new RuntimeException("Aucune donnée dans le fichier");
            }

            repository.deleteAll();

            rows.next();

            List<AdministrationTempo> list = new ArrayList<>();

            while (rows.hasNext()) {
                Row row = rows.next();

                if (row.getCell(0) == null) continue;

                AdministrationTempo tempo = AdministrationTempo.builder()
                        .matriElev(getString(row, 0))
                        .nomPrenoms(getString(row, 1))
                        .groupe(getString(row, 2))
                        .codeUE(getString(row, 3))
                        .ecue1(getString(row, 4))
                        .ecue2(getString(row, 5))
                        .dteDeliber(getString(row, 6))
                        .moyenneCC(getDouble(row, 7))
                        .moyenneExam(getDouble(row, 8))
                        .moyenneGle(getDouble(row, 9))
                        .decision(getString(row, 10))
                        .annee(getString(row, 11))
                        .dateOperation(getString(row, 12))
                        .creditUE(getInteger(row, 13))
                        .classActu(getString(row, 14))
                        .classExam(getString(row, 15))
                        .traitement(AdministrationEnum.NON_TRAITE)
                        .build();

                list.add(tempo);
            }

            if (list.isEmpty()) {
                throw new RuntimeException("Le fichier ne contient aucune ligne valide");
            }

            repository.saveAll(list);
        }
    }

    /** LISTE POUR LE FRONT */
    public List<AdministrationTempoDto> findAll() {
        return repository.findAll().stream()
                .map(a -> AdministrationTempoDto.builder()
                        .id(a.getId())
                        .matriElev(a.getMatriElev())
                        .nomPrenoms(a.getNomPrenoms())
                        .groupe(a.getGroupe())
                        .codeUE(a.getCodeUE())
                        .moyenneGle(a.getMoyenneGle())
                        .decision(a.getDecision())
                        .traitement(a.getTraitement())
                        .build())
                .toList();
    }

    private String getString(Row row, int index) {
        Cell cell = row.getCell(index);
        return cell == null ? null : cell.toString();
    }

    private Double getDouble(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return null; // ou throw new RuntimeException("Valeur non numérique")
                }
            case FORMULA:
                return cell.getNumericCellValue();
            default:
                return null;
        }
    }

    private Integer getInteger(Row row, int index) {
        Double val = getDouble(row, index);
        return val == null ? null : val.intValue();
    }


    public void traiterAdministrationTempo() {

        List<AdministrationTempo> lignes =
                repository.findAll().stream()
                        .filter(a -> a.getTraitement() == AdministrationEnum.NON_TRAITE)
                        .toList();

        for (AdministrationTempo tempo : lignes) {

            String codeVerification = resolveCodeUE(tempo);

            Optional<Deliberer> delibererOpt =
                    delibererRepository.findByIdGroupeAndMatriElevAndCodeUE(
                            tempo.getGroupe(),
                            tempo.getMatriElev(),
                            codeVerification
                    );

            if (delibererOpt.isPresent()) {
                Deliberer deliberer = delibererOpt.get();

                boolean changed = false;

                if (!equalsDouble(deliberer.getMoyenne(), tempo.getMoyenneGle())) {
                    deliberer.setMoyenne(tempo.getMoyenneGle());
                    changed = true;
                }

                if (!safeEquals(deliberer.getClassActu(), tempo.getClassActu())) {
                    deliberer.setClassActu(tempo.getClassActu());
                    changed = true;
                }

                if (!safeEquals(deliberer.getClassExam(), tempo.getClassExam())) {
                    deliberer.setClassExam(tempo.getClassExam());
                    changed = true;
                }

                if (changed) {
                    delibererRepository.save(deliberer);
                }

            } else {
                Deliberer deliberer = Deliberer.builder()
                        .idGroupe(tempo.getGroupe())
                        .codeUE(codeVerification)
                        .matriElev(tempo.getMatriElev())
                        .nomPrenoms(tempo.getNomPrenoms())
                        .moyenne(tempo.getMoyenneGle())
                        .annee(tempo.getAnnee())
                        .dteDeliber(tempo.getDteDeliber())
                        .desDeliber(tempo.getDecision())
                        .creditUE(tempo.getCreditUE())
                        .classActu(tempo.getClassActu())
                        .classExam(tempo.getClassExam())
                        .dateOperation(tempo.getDateOperation())
                        .build();

                delibererRepository.save(deliberer);
            }

            tempo.setTraitement(AdministrationEnum.TRAITE);
            repository.save(tempo);
        }
    }

    private String resolveCodeUE(AdministrationTempo tempo) {
        if (tempo.getCodeUE() != null && !tempo.getCodeUE().isBlank()) {
            return tempo.getCodeUE();
        }
        if (tempo.getEcue1() != null && !tempo.getEcue1().isBlank()) {
            return tempo.getEcue1();
        }
        return tempo.getEcue2();
    }

    private boolean safeEquals(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    private boolean equalsDouble(Double a, Double b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return Double.compare(a, b) == 0;
    }
}
