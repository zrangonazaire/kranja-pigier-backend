package com.pigierbackend.etatrecapinscrianne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reports")
@Tag(name = "Reporting API", description = "API pour la génération de rapports PDF")
public class ReportController {
    
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    
    @Autowired
    private JasperReportService reportService;
    
    @GetMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(
        summary = "Générer un rapport PDF",
        description = "Génère un rapport PDF basé sur les paramètres fournis",
        responses = {
            @ApiResponse(responseCode = "200", description = "Rapport PDF généré avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
        }
    )
  
    public ResponseEntity<?> generateReport(
            @Parameter(description = "Niveau scolaire", example = "LP1", required = true)
            @RequestParam String niveau,
            
            @Parameter(description = "Campus", example = "ABIDJAN PLATEAU", required = true)
            @RequestParam String campus,
            
            @Parameter(description = "Année de début", example = "2023", required = true)
            @RequestParam String anneedebut,

            @Parameter(description = "Année de fin", example = "2024", required = true)
            @RequestParam String anneefin) {
        
        logger.info("Requête reçue - Niveau: {}, Campus: {}, Année: {}/{}", 
                   niveau, campus, anneedebut, anneefin);
        
        try {
            // Validation des paramètres
            if (niveau == null || niveau.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le paramètre 'niveau' est requis");
            }
            if (campus == null || campus.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le paramètre 'campus' est requis");
            }
            if (anneedebut == null || anneedebut.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le paramètre 'anneedebut' est requis");
            }
            if (anneefin == null || anneefin.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Le paramètre 'anneefin' est requis");
            }
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Niveau", niveau.trim());
            parameters.put("Campus", campus.trim());
            parameters.put("Annee", anneedebut.trim() + "/" + anneefin.trim());
            
            logger.debug("Paramètres envoyés à Jasper: {}", parameters);
            
            byte[] pdfBytes = reportService.generateReport(parameters);
            
            if (pdfBytes == null || pdfBytes.length == 0) {
                logger.error("Le PDF généré est vide");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur: le PDF généré est vide");
            }
            
            logger.info("Rapport généré avec succès - Taille: {} bytes", pdfBytes.length);
            
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                       "attachment; filename=\"rapport_eleves_" + niveau + "_" + campus + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
            
        } catch (JRException e) {
            logger.error("Erreur JasperReports: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de la génération du rapport: " + e.getMessage());
        } catch (SQLException e) {
            logger.error("Erreur base de données: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur de connexion à la base de données: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur inattendue: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur interne du serveur: " + e.getMessage());
        }
    }
}