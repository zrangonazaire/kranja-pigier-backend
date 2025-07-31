package com.pigierbackend.preinscription;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.data.domain.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
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

@DependsOnDatabaseInitialization
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
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateInscription"));
        return preinscriptionYakroRepository.findAll(pageable)
                .stream()
                .map(preinscriptionYakroMapper::fromPreinscription)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public PreinscriptionResponseDto getPreinscriptionById(String id) {
        return preinscriptionYakroRepository.findById(id)
                .map(preinscriptionYakroMapper::fromPreinscription)
                .orElseThrow(() -> new IllegalArgumentException("La péinscription n'a pas été trouvée"));
    }

    @Override
    public PreinscriptionResponseDto createOrUpdatePreinscription(
            PreinscriptionRequestDto preinscriptionYakroRequestDto) {
        log.info("Création ou mise à jour de la préinscription : {}", preinscriptionYakroRequestDto);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0) // Default hour
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0) // Default minute
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0) // Default second
                .toFormatter();

        LocalDateTime dateNaissance = LocalDateTime.parse(preinscriptionYakroRequestDto.getDateNaissance(), formatter);
        PREINSCRIPTION preinscriptionyakro = preinscriptionYakroRepository
                .findById(preinscriptionYakroRequestDto.getId()).orElseGet(() -> {
                    PREINSCRIPTION p = new PREINSCRIPTION();
                    p.setDateInscription(LocalDateTime.now());
                    p = preinscriptionYakroMapper.toPreinscription(preinscriptionYakroRequestDto);
                    p.setDateNaissance(dateNaissance);
                    return p;
                });

        BeanUtils.copyProperties(preinscriptionYakroRequestDto, preinscriptionyakro);
        preinscriptionyakro.setDateInscription(LocalDateTime.now());
        preinscriptionYakroRepository.save(preinscriptionyakro);
        return preinscriptionYakroMapper.fromPreinscription(preinscriptionyakro);
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
                .filter(x -> x.getNomPrenoms().contains(nomEleve))
                .map(preinscriptionYakroMapper::fromPreinscription)
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
        // JasperReport jasperreport =
        // JasperCompileManager.compileReport(file.getAbsolutePath());
        // File di = new File(path + "/depot_etat");
        // boolean dir = di.mkdir();
        // if (dir) {
        // System.out.println("Dossier cree");

        // }
        // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport,
        // parameters, dataSource.getConnection());
        // JasperExportManager.exportReportToPdfFile(jasperPrint, path +
        // "/depot_etat/incriptioneport" + id + ".pdf");
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
        PageRequest pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "dateInscription"));

        return preinscriptionYakroRepository.findAll(pageable)
                .stream()
                .map(preinscriptionYakroMapper::fromPreinscription)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<PreinscriptionResponseDto> getAllPreinscription() {
        return preinscriptionYakroRepository.findAll(Sort.by(Sort.Direction.DESC, "dateInscription"))
                .stream()
                .map(preinscriptionYakroMapper::fromPreinscription)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<PreinscriptionResponseDto> getAllPreinscriptionEntreDeuxDate(LocalDateTime debut, LocalDateTime fin) {
        // The end date should be exclusive for a 'between' query that includes the
        // whole day.
        LocalDateTime finExclusive = fin.plusDays(1).withHour(0).withMinute(0).withSecond(0);
        return preinscriptionYakroRepository
                .findByDateInscriptionBetween(debut, finExclusive, Sort.by(Sort.Direction.DESC, "dateInscription"))
                .stream()
                .map(preinscriptionYakroMapper::fromPreinscription)
                .distinct()
                .collect(Collectors.toList());
    }

}
