package com.pigierbackend.administration;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdministrationService {

    private final AdministrationRepository repository;
    private final DelibererRepository delibererRepository;

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
                        .moyenneCC(round2(getDouble(row, 7)))
                        .moyenneExam(round2(getDouble(row, 8)))
                        .moyenneGle(round2(getDouble(row, 9)))
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

    public List<AdministrationTempoDto> findAll() {
        return repository.findAll().stream()
                .map(a -> AdministrationTempoDto.builder()
                        .id(a.getId())
                        .matriElev(a.getMatriElev())
                        .nomPrenoms(a.getNomPrenoms())
                        .groupe(a.getGroupe())
                        .codeUE(a.getCodeUE())
                        .ecue1(a.getEcue1())
                        .ecue2(a.getEcue2())
                        .dteDeliber(a.getDteDeliber())
                        .moyenneCC(a.getMoyenneCC())
                        .moyenneExam(a.getMoyenneExam())
                        .moyenneGle(a.getMoyenneGle())
                        .decision(a.getDecision())
                        .annee(a.getAnnee())
                        .dateOperation(a.getDateOperation())
                        .creditUE(a.getCreditUE())
                        .classActu(a.getClassActu())
                        .classExam(a.getClassExam())
                        .traitement(a.getTraitement())
                        .build())
                .toList();
    }

    private String getString(Row row, int index) {
        Cell cell = row.getCell(index);
        return cell == null ? null : cell.toString();
    }

    private Double round2(Double value) {
        if (value == null) return null;
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Double getDouble(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;

        try {
            return switch (cell.getCellType()) {
                case NUMERIC -> cell.getNumericCellValue();
                case STRING -> Double.parseDouble(cell.getStringCellValue().replace(",", "."));
                case FORMULA -> cell.getNumericCellValue();
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }

    private Integer getInteger(Row row, int index) {
        Double val = getDouble(row, index);
        return val == null ? null : val.intValue();
    }

    @Transactional
    public void traiterAdministrationTempo() {

        List<AdministrationTempo> lignes =
                repository.findByTraitement(AdministrationEnum.NON_TRAITE);

        for (AdministrationTempo tempo : lignes) {

            if (isBlank(tempo.getMatriElev())
                    || isBlank(tempo.getGroupe())
                    || isBlank(resolveCodeUE(tempo))) {

                tempo.setTraitement(AdministrationEnum.TRAITE);
                repository.save(tempo);
                continue;
            }

            String codeUE = resolveCodeUE(tempo);

            Optional<Deliberer> delibererOpt =
                    delibererRepository.findByIdGroupeAndCodeUEAndMatriElev(
                            tempo.getGroupe(),
                            codeUE,
                            tempo.getMatriElev()
                    );

            if (delibererOpt.isPresent()) {
                Deliberer deliberer = delibererOpt.get();
                boolean changed = false;

                if (!equalsDouble(deliberer.getMoyenne(), tempo.getMoyenneGle())) {
                    deliberer.setMoyenne(tempo.getMoyenneGle());
                    changed = true;
                }

                if (!safeEquals(deliberer.getDesDeliber(), tempo.getDecision())) {
                    deliberer.setDesDeliber(tempo.getDecision());
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

                if (!safeEquals(deliberer.getAnnee(), tempo.getAnnee())) {
                    deliberer.setAnnee(tempo.getAnnee());
                    changed = true;
                }

                if (!safeEquals(deliberer.getDteDeliber(), tempo.getDteDeliber())) {
                    deliberer.setDteDeliber(tempo.getDteDeliber());
                    changed = true;
                }

                if (!safeEquals(deliberer.getDateOperation(), tempo.getDateOperation())) {
                    deliberer.setDateOperation(tempo.getDateOperation());
                    changed = true;
                }

                if (!safeEquals(deliberer.getNomPrenoms(), tempo.getNomPrenoms())) {
                    deliberer.setNomPrenoms(tempo.getNomPrenoms());
                    changed = true;
                }

                if (tempo.getCreditUE() != null
                        && !tempo.getCreditUE().equals(deliberer.getCreditUE())) {
                    deliberer.setCreditUE(tempo.getCreditUE());
                    changed = true;
                }

                if (changed) {
                    delibererRepository.save(deliberer);
                }

            } else {
                Deliberer deliberer = Deliberer.builder()
                        .idGroupe(tempo.getGroupe())
                        .codeUE(codeUE)
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

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
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
