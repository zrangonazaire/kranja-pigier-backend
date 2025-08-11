package recaptinscri;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

@RestController

@CrossOrigin("*")
@Tag(name = "RECAPINSCR", description = "Eleve Management INSCRIT APIs")
@RequestMapping("/recap-inscri")
public class RecapInscritController {
    
    @Autowired
    private RecapInscriService excelExportService;
    
    @GetMapping("/recapinscrits")
    public ResponseEntity<?> exportRecapInscrits(
            @RequestParam String anneeAcademique) {
        
        System.out.println("Début de génération du fichier Excel pour l'année académique: " + anneeAcademique);
        
        try {
            byte[] excelBytes = excelExportService.generateExcelFile(anneeAcademique);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("Recapitulatif_des_inscrits_" + anneeAcademique.replace("/", "_") + ".xlsx")
                    .build());
            
            System.out.println("Fichier Excel généré avec succès");
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            System.out.println("ERREUR - Problème lors de la génération du fichier Excel: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la génération du fichier Excel");
            
        } catch (Exception e) {
            System.out.println("ERREUR INATTENDUE: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur inattendue est survenue");
        }
    }
}