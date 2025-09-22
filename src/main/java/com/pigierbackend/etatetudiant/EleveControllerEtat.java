package com.pigierbackend.etatetudiant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/etateleveexcel")
@CrossOrigin(origins = "*")
public class EleveControllerEtat {
    
    private static final Logger logger = LoggerFactory.getLogger(EleveControllerEtat.class);
    
    @Autowired
    private ExcelService excelService;
    
    @GetMapping(value = "/excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<?> generateExcel(
            @RequestParam String anneeSco,
            @RequestParam String etabSource,
            @RequestParam String niveau,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        
        try {
            // Validation des paramètres
            if (anneeSco == null || anneeSco.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Le paramètre 'anneeSco' est requis"));
            }
            
            if (etabSource == null || etabSource.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Le paramètre 'etabSource' est requis"));
            }
            
            if (niveau == null || niveau.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Le paramètre 'niveau' est requis"));
            }
            
            if (startDate == null || endDate == null) {
                return ResponseEntity.badRequest().body(createErrorResponse("Les dates de début et de fin sont requises"));
            }
            
            if (startDate.after(endDate)) {
                return ResponseEntity.badRequest().body(createErrorResponse("La date de début doit être antérieure à la date de fin"));
            }
            
            logger.info("Génération Excel - Année: {}, Campus: {}, Niveau: {}, Période: {} à {}", 
                       anneeSco, etabSource, niveau, startDate, endDate);
            
            byte[] excelContent = excelService.generateExcelFile(anneeSco, etabSource, niveau, startDate, endDate);
            
            // Vérifier si des données ont été trouvées
            if (excelContent == null || excelContent.length == 0) {
                logger.warn("Aucune donnée trouvée pour les critères spécifiés");
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(createErrorResponse("Aucune donnée trouvée pour les critères de recherche"));
            }
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", 
                "eleves_" + anneeSco.replace("/", "_") + "_" + etabSource + ".xlsx");
            headers.setContentLength(excelContent.length);
            
            logger.info("Fichier Excel généré avec succès - Taille: {} bytes", excelContent.length);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelContent);
                     
        } catch (IllegalArgumentException e) {
            logger.error("Erreur de paramètre: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(createErrorResponse("Paramètres invalides: " + e.getMessage()));
            
        } catch (org.springframework.dao.DataAccessException e) {
            logger.error("Erreur d'accès aux données: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erreur d'accès à la base de données"));
            
        } catch (java.io.IOException e) {
            logger.error("Erreur d'écriture Excel: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erreur lors de la génération du fichier Excel"));
            
        } catch (Exception e) {
            logger.error("Erreur inattendue: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Erreur interne du serveur: " + e.getMessage()));
        }
    }
    
    // Méthode utilitaire pour créer des réponses d'erreur structurées
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("timestamp", new Date());
        return response;
    }
    
    // Endpoint pour vérifier la santé de l'API
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "Excel Export Service");
        response.put("timestamp", new Date());
        return ResponseEntity.ok(response);
    }
}