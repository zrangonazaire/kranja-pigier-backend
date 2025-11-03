package com.pigierbackend.etatetudiantnew;

import com.pigierbackend.etatetudiantnew.EtudiantExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/etudiantsExcelnew")
@CrossOrigin(origins = "*")
public class EtudiantController {

    @Autowired
    private EtudiantExcelService excelService;

    @GetMapping(value ="/export-excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<?> exportEtudiantsToExcel(
            @RequestParam String annee,
            @RequestParam String etab,
            @RequestParam String classe) {
        
        try {
            // Validation des paramètres
            if (annee == null || annee.trim().isEmpty()) {
                return createErrorResponse("Le paramètre 'annee' est obligatoire", HttpStatus.BAD_REQUEST);
            }
            if (etab == null || etab.trim().isEmpty()) {
                return createErrorResponse("Le paramètre 'etab' est obligatoire", HttpStatus.BAD_REQUEST);
            }
            if (classe == null || classe.trim().isEmpty()) {
                return createErrorResponse("Le paramètre 'classe' est obligatoire", HttpStatus.BAD_REQUEST);
            }

            // Génération du fichier Excel
            byte[] excelFile = excelService.generateExcelFile(annee, etab, classe);
            
            // Vérification si des données ont été trouvées
            if (excelFile == null || excelFile.length == 0) {
                return createErrorResponse("Aucune donnée trouvée pour les critères spécifiés", HttpStatus.NOT_FOUND);
            }
            
            String fileName = String.format("Liste_Etudiants_%s_%s.xlsx", 
                classe, 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));
            
            // Retourner le fichier Excel
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelFile);
                    
        } catch (IOException e) {
            // Erreur de génération du fichier
            System.err.println("Erreur IO lors de la génération Excel: " + e.getMessage());
            e.printStackTrace();
            return createErrorResponse("Erreur lors de la génération du fichier Excel: " + e.getMessage(), 
                                     HttpStatus.INTERNAL_SERVER_ERROR);
                    
        } catch (IllegalArgumentException e) {
            // Erreur de paramètres invalides
            System.err.println("Paramètre invalide: " + e.getMessage());
            return createErrorResponse("Paramètre invalide: " + e.getMessage(), HttpStatus.BAD_REQUEST);
                    
        } catch (Exception e) {
            // Erreur générale
            System.err.println("Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
            return createErrorResponse("Erreur interne du serveur: " + e.getMessage(), 
                                     HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Méthode utilitaire pour créer une réponse d'erreur structurée
     */
    private ResponseEntity<Map<String, Object>> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        errorResponse.put("path", "/etudiantsExcelnew/export-excel");
        
        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * Endpoint pour vérifier que l'API est fonctionnelle
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("service", "Excel Export API");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint pour tester les paramètres sans générer de fichier
     */
    @GetMapping("/test-params")
    public ResponseEntity<Map<String, Object>> testParameters(
            @RequestParam String annee,
            @RequestParam String etab,
            @RequestParam String classe) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("annee", annee);
        response.put("etab", etab);
        response.put("classe", classe);
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "Paramètres reçus avec succès");
        
        return ResponseEntity.ok(response);
    }
}