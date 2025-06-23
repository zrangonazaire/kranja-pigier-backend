package com.pigierbackend.preinscription;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.data.domain.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@RequiredArgsConstructor
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PreinscriptionServiceImpl implements PreinscriptionService {

    final PreinscriptionRepository preinscriptionYakroRepository;
    final PreinscriptionMapper preinscriptionYakroMapper;
    final DataSource dataSource;

    @Override
    public List<PreinscriptionResponseDto> getAllPreinscription(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "datinscrip"));
        return preinscriptionYakroRepository.findAll(pageable)
                .stream()
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public PreinscriptionResponseDto getPreinscriptionById(String id) {
        return preinscriptionYakroRepository.findById(id)
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .orElseThrow(() -> new IllegalArgumentException("La péinscription n'a pas été trouvée"));
    }

    @Override
    public PreinscriptionResponseDto createOrUpdatePreinscription(
            PreinscriptionRequestDto preinscriptionYakroRequestDto) {
        PREINSCRIPTION preinscriptionyakro = preinscriptionYakroRepository
                .findById(preinscriptionYakroRequestDto.getId()).orElse(new PREINSCRIPTION());
        BeanUtils.copyProperties(preinscriptionYakroRequestDto, preinscriptionyakro);
        preinscriptionYakroRepository.save(preinscriptionyakro);
        return preinscriptionYakroMapper.fromPreinscriptionYakro(preinscriptionyakro);
    }

    @Override
    public Boolean deletePreinscription(String id) {
        Optional<PREINSCRIPTION> preinscYak = preinscriptionYakroRepository.findById(id);
        if (preinscYak.isPresent()) {
            preinscriptionYakroRepository.delete(preinscYak.get());
            return true;
        }
        return false;
    }

    @Override
    public List<PreinscriptionResponseDto> getPreinscriptionByNomEleve(String nomEleve) {
        return preinscriptionYakroRepository.findAll()
                .stream()
                .filter(x -> x.getNomprenoms().contains(nomEleve))
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .collect(Collectors.toList());
    }

    @Override
    public byte[] impressionPreinscription(String id) throws FileNotFoundException, JRException, SQLException {

        String path = "src/main/resources/etat/template";

        File file = ResourceUtils.getFile(path + "/fichierpreincription.jrxml");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id_param", id);
        JasperReport jasperreport = JasperCompileManager.compileReport(file.getAbsolutePath());
        File di = new File(path + "/depot_etat");
        boolean dir = di.mkdir();
        if (dir) {
            System.out.println("Dossier cree");

        }
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport, parameters, dataSource.getConnection());
        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/depot_etat/fichierpreincription" + id + ".pdf");
        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    @Override
    public byte[] impressionInscription(String id) throws FileNotFoundException, JRException, SQLException {
        // String path = "src/main/resources/etat/template";

        // File file = ResourceUtils.getFile(path + "/incriptioneport.jrxml");
        // Map<String, Object> parameters = new HashMap<>();
        // parameters.put("id_param", id);
        // JasperReport jasperreport = JasperCompileManager.compileReport(file.getAbsolutePath());
        // File di = new File(path + "/depot_etat");
        // boolean dir = di.mkdir();
        // if (dir) {
        //     System.out.println("Dossier cree");

        // }
        // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport, parameters, dataSource.getConnection());
        // JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/depot_etat/incriptioneport" + id + ".pdf");
        // return JasperExportManager.exportReportToPdf(jasperPrint);

          try {

            String path = "src/main/resources/etat/template";
            File file = ResourceUtils.getFile(path + "/incriptioneport.jrxml");
         log.info("******reportPath: " + file);
            // Charger le rapport
            JasperReport jasperreport = JasperCompileManager.compileReport(file.getAbsolutePath());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id_param", id);
            File di = new File(path + "/depot_etat");
            boolean dir = di.mkdir();
            if (dir) {
                System.out.println("Dossier crée");
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport, parameters,
                    dataSource.getConnection());
          JasperExportManager.exportReportToPdfFile(jasperPrint, path +
         "/depot_etat/ficheprinscr" + id + ".pdf");
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du rapport : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur: " + e.getMessage());
        } finally {
            // Fermer les ressources si nécessaire
            if (dataSource != null) {
                try {
                    dataSource.getConnection().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public byte[] impressionFicheMedicale(String id) throws FileNotFoundException, JRException, SQLException {

        try {

            String path = "src/main/resources/etat/template";
            File file = ResourceUtils.getFile(path + "/medicalreport.jrxml");
            System.out.println("******reportPath: " + file);
            // Charger le rapport
            JasperReport jasperreport = JasperCompileManager.compileReport(file.getAbsolutePath());
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id_param", id);
            File di = new File(path + "/depot_etat");
            boolean dir = di.mkdir();
            if (dir) {
                System.out.println("Dossier cree");
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport, parameters,
                    dataSource.getConnection());
          JasperExportManager.exportReportToPdfFile(jasperPrint, path +
         "/depot_etat/medicalreport" + id + ".pdf");
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du rapport : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur: " + e.getMessage());
        } finally {
            // Fermer les ressources si nécessaire
            if (dataSource != null) {
                try {
                    dataSource.getConnection().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<PreinscriptionResponseDto> getAllPreinscription(int size) {
        PageRequest pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "datinscrip"));

        return preinscriptionYakroRepository.findAll(pageable)
                .stream()
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<PreinscriptionResponseDto> getAllPreinscription() {
        return preinscriptionYakroRepository.findAll(Sort.by(Sort.Direction.DESC, "datinscrip"))
                .stream()
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<PreinscriptionResponseDto> getAllPreinscriptionEntreDeuxDate(LocalDate debut, LocalDate fin) {
        return preinscriptionYakroRepository.findAll(Sort.by(Sort.Direction.DESC, "datinscrip"))
                .stream()
                .filter(preinscription -> !preinscription.getDatinscrip().isBefore(debut) &&
                        !preinscription.getDatinscrip().isAfter(fin))
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .distinct()
                .collect(Collectors.toList());
    }

}
