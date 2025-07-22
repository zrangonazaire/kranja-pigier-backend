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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            System.out.println("Erreur lors de la génération du rapport : " + e.getMessage());
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
            headers.setContentDispositionFormData("filename", "pointDeCaisseDroitInsEntreDeuxDate.pdf"); // Nom du fichier

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);
        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du rapport : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
