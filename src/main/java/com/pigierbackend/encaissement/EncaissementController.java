package com.pigierbackend.encaissement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/encaissement")
@Tag(name = "Encaissement", description = "Encaissement Management APIs")
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EncaissementController {
    final EncaissementService encaissementService;

    @GetMapping("/journalEncaissementsBetweenDates")
@PreAuthorize("hasAuthority('READ_ENCAISSEMENT')")
    public ResponseEntity<byte[]> generateJournalEncaissementsBetweenDatesReport(
            @RequestParam List<String> modeRegParam,
            @RequestParam List<String> etablissementSourceParam,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateFin,
            @RequestParam Integer paramIDcaisse) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Mode_Reg_Param", modeRegParam);
            params.put("Etablissement_Source_Param", etablissementSourceParam);
            params.put("ParamDate_Debut", paramDateDebut);
            params.put("ParamDate_fIN", paramDateFin);
            params.put("ParamIDcaisse", paramIDcaisse);

            byte[] reportBytes = encaissementService.generateJournalEncaissementsBetweenDatesReport(params);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "pointDeCaisseEntreDeuxDate.pdf"); // Nom du fichier

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);
        } catch (Exception e) {
            System.out.println("Erreur lors de la gÃ©nÃ©ration du rapport : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/journalDroitInscBetweenDates")
@PreAuthorize("hasAuthority('READ_ENCAISSEMENT')")
    public ResponseEntity<byte[]> generateJournalEncaissementsDroitInscriBetweenDatesReport(
            @RequestParam List<String> modeRegParam,
            @RequestParam List<String> etablissementSourceParam,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateFin,
            @RequestParam Integer paramIDcaisse) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Mode_Reg_Param", modeRegParam);
            params.put("Etablissement_Source_Param", etablissementSourceParam);
            params.put("ParamDate_Debut", paramDateDebut);
            params.put("ParamDate_fIN", paramDateFin);
            params.put("ParamIDcaisse", paramIDcaisse);

            byte[] reportBytes = encaissementService.generateJournalDroitInscrisBetweenDatesReport(params);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "pointDeCaisseDroitInsEntreDeuxDate.pdf"); // Nom du
                                                                                                         // fichier

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);
        } catch (Exception e) {
            System.out.println("Erreur lors de la gÃ©nÃ©ration du rapport : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/etatFacturation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EncaissementDTO>> generateEtatFacturationReport(
            @RequestParam List<String> etablissementSourceParam,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateFin) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("etablissementSources", etablissementSourceParam);
            params.put("dateDebut", paramDateDebut);
            params.put("dateFin", paramDateFin);

            List<EncaissementDTO> reportData = encaissementService.getEncaissementsEntreDeuxPeriodeEtabSource(params);
            return ResponseEntity.ok(reportData);
        } catch (Exception e) {
            System.out.println("Erreur lors de la gÃ©nÃ©ration du rapport : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/fichierExcleDeFacturation")
    public ResponseEntity<byte[]> generateFichierExcelDeFacturation(
            @RequestParam List<String> etablissementSourceParam,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateFin) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("etablissementSources", etablissementSourceParam);
            params.put("dateDebut", paramDateDebut);
            params.put("dateFin", paramDateFin);

            byte[] reportBytes = encaissementService.generateEtatFacturationReport(params);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("filename", "facturation.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);
        } catch (Exception e) {
            System.out.println("Erreur lors de la gÃ©nÃ©ration du rapport : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/fichierExcelChiffreAffaire")
    public ResponseEntity<byte[]> generateChiffreAffaireExcelDeFacturation(
            @RequestParam List<String> etablissementSourceParam,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateFin) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("etablissementSources", etablissementSourceParam);
            params.put("dateDebut", paramDateDebut);
            params.put("dateFin", paramDateFin);

            byte[] reportBytes = encaissementService.generateEtatChiffreAffaireReport(params);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("filename", "chiffre_affaire.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);
        } catch (Exception e) {
            System.out.println("Erreur lors de la gÃ©nÃ©ration du rapport : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
@GetMapping(value = "/listeChiffreChiffreAffaire", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EncaissementDTO>> listeChiffreChiffreAffaire(
            @RequestParam List<String> etablissementSourceParam,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateFin) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("etablissementSources", etablissementSourceParam);
            params.put("dateDebut", paramDateDebut);
            params.put("dateFin", paramDateFin);

            List<EncaissementDTO> reportData = encaissementService.getEncaissementsChiffreAffaireEntreDeuxPeriodeEtabSource(params);
            return ResponseEntity.ok(reportData);
        } catch (Exception e) {
            System.out.println("Erreur lors de la gÃ©nÃ©ration du rapport : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/listeFacturation",produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<List<EncaissementDTO>> listeFacturation(
            @RequestParam List<String> etablissementSourceParam,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date paramDateFin) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("etablissementSources", etablissementSourceParam);
            params.put("dateDebut", paramDateDebut);
            params.put("dateFin", paramDateFin);

            List<EncaissementDTO> reportData = encaissementService.getEncaissementsEntreDeuxPeriodeEtabSource(params);
            return ResponseEntity.ok(reportData);
        } catch (Exception e) {
            System.out.println("Erreur lors de la gÃ©nÃ©ration du rapport : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
