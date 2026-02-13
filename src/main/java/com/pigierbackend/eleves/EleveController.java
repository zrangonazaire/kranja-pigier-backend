package com.pigierbackend.eleves;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eleves")
@Slf4j
@Tag(name = "Eleves", description = "Eleve Management APIs")
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EleveController {

    final EleveService eleveService;

//    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LIRE_ELEVE')")
    @GetMapping(value = "/etatListeEtudiant", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> etatListeEtudiant(@RequestParam String paramClasse,
            @RequestParam String paramAnneDebut, @RequestParam String paramAnneFin, @RequestParam String paramEtab)
            throws Exception {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("PARAMEANNE", paramAnneDebut + "/" + paramAnneFin);
            params.put("PARAMCLASSE", paramClasse);
            params.put("PARAMETAB", paramEtab);

            log.info("paramClasse: {}", paramClasse);
            byte[] reportBytes = eleveService.listeEtudiant(params);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "etatListeEtudiant.pdf"); // Nom du fichier

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);

        } catch (Exception e) {
            log.error("Erreur lors de la gÃ©nÃ©ration du rapport PDF : {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // return eleveService.listeEtudiant(params);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LIRE_ELEVE')")
    @GetMapping("/etatListeEtudiantExcel")
    public ResponseEntity<byte[]> etatListeEtudiantExcel(@RequestParam String paramClasse,
            @RequestParam String paramAnneDebut, @RequestParam String paramAnneFin, @RequestParam String paramEtab)
            throws Exception {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("PARAMEANNE", paramAnneDebut + "/" + paramAnneFin);
            params.put("PARAMCLASSE", paramClasse);
            params.put("PARAMETAB", paramEtab);

            log.info("paramClasse: {}", paramClasse);
            byte[] reportBytes = eleveService.listeEtudiantExcel(params);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("filename", "etatListeEtudiant.xlsx"); // Nom du fichier

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);

        } catch (Exception e) {
            log.error("Erreur lors de la gÃ©nÃ©ration du rapport Excel : {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add your endpoint methods here
    // For example:
    // @GetMapping("/example")
    // public ResponseEntity<ExampleResponse> getExample() {
    // return ResponseEntity.ok(new ExampleResponse("Hello, World!"));
    // }
//    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LIRE_ELEVE')")
    @GetMapping(value = "/getPromotionsEleves", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EleveRecordDTO>> getPromotionsEleves(
            @RequestParam(required = false) List<String> promotions,
            @RequestParam(required = false) List<String> etablissements,
            @RequestParam String anneeScolaire,
            @RequestParam(required = false) String startStr,
            @RequestParam(required = false) String endStr,
            @RequestParam(required = false) String nomElev,
            @RequestParam(required = false) String matriElev) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDate = (startStr == null || startStr.isBlank()) ? null : LocalDate.parse(startStr, formatter);
        LocalDate endDate   = (endStr == null || endStr.isBlank())   ? null : LocalDate.parse(endStr, formatter);

        // Nettoyage des listes vides envoyées comme "" dans l'URL
        if (promotions != null && promotions.size() == 1 && promotions.get(0).isBlank()) {
            promotions = null;
        }
        if (etablissements != null && etablissements.size() == 1 && etablissements.get(0).isBlank()) {
            etablissements = null;
        }

        List<EleveRecordDTO> promotionEleves = eleveService.getPromotionsEleves(
                promotions, etablissements, anneeScolaire, startDate, endDate, nomElev, matriElev);
        return ResponseEntity.ok(promotionEleves);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LIRE_ELEVE')")
   @GetMapping(value = "/getPromotionsElevesPayer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EleveRecordAvecPayerDto>> getPromotionsElevesPayer(@RequestParam List<String> promotions,
            @RequestParam List<String> etablissements, @RequestParam String anneeScolaire,
            @RequestParam String startStr, @RequestParam String endStr, @RequestParam int montantpayer) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startStr, formatter);
        LocalDate endDate = LocalDate.parse(endStr, formatter);
        List<EleveRecordAvecPayerDto> promotionEleves = eleveService.getPromotionsElevesAvecMontantPayer(promotions, etablissements,
                anneeScolaire, startDate, endDate, montantpayer);
        return ResponseEntity.ok(promotionEleves);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LIRE_ELEVE')")
    @GetMapping(value = "/getPromotionsElevesExcel")
    public ResponseEntity<byte[]> getPromotionsElevesExcel(@RequestParam List<String> promotions,
            @RequestParam List<String> etablissements, @RequestParam String anneeScolaire,
            @RequestParam String startStr,
            @RequestParam String endStr) throws Exception {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(startStr, formatter);
            LocalDate endDate = LocalDate.parse(endStr, formatter);
            log.info("Dates parsed for Excel: startDate={}, endDate={}", startDate, endDate);
            byte[] excelData = eleveService.getPromotionsElevesExcel(promotions, etablissements, anneeScolaire,
                    startDate, endDate);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("filename", "promotions_eleves.xlsx");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);
        } catch (Exception e) {
            log.error("Erreur lors de la gÃ©nÃ©ration du rapport Excel pour les promotions : {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LIRE_ELEVE')")
    @GetMapping(value = "/getAllClasses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllClasses(@RequestParam String anneeScolaire) throws Exception {
        try {
            List<String> classes = eleveService.getAllClasses(anneeScolaire);
            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            log.error("Erreur lors de la rÃ©cupÃ©ration des classes : {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LIRE_ELEVE')")
    @GetMapping("/getPromotionsElevesExcels")
    public ResponseEntity<byte[]> getPromotionsElevesExcels(
            @RequestParam List<String> promotions,
            @RequestParam List<String> etablissements,
            @RequestParam String anneeScolaire,
            @RequestParam String startStr,
            @RequestParam String endStr) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startStr, formatter);
        LocalDate endDate = LocalDate.parse(endStr, formatter);

        byte[] excelData = eleveService.getPromotionsElevesExcels(
                promotions, etablissements, anneeScolaire, startDate, endDate
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                )
        );
        headers.setContentDispositionFormData(
                "attachment", "promotions_eleves.xlsx"
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelData);
    }


}
