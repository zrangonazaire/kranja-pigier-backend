package com.pigierbackend.eleves;

import java.util.HashMap;
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
@GetMapping("/etatListeEtudiant")
    public ResponseEntity<byte[]> etatListeEtudiant(@RequestParam String paramClasse, @RequestParam String paramAnneDebut, @RequestParam String paramAnneFin ) throws Exception {
       try {
             Map<String, Object> params = new HashMap<>();
            params.put("PARAMEANNE", paramAnneDebut+"/"+paramAnneFin);
            params.put("PARAMCLASSE", paramClasse);
       
            log.info("paramClasse: {}" , paramClasse);
            byte[] reportBytes = eleveService.listeEtudiant(params);

             HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "etatListeEtudiant.pdf"); // Nom du fichier

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);


       } catch (Exception e) {
      System.out.println("Erreur lors de la génération du rapport : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
       

       //  return eleveService.listeEtudiant(params);
    }


    // Add your endpoint methods here
    // For example:
    // @GetMapping("/example")
    // public ResponseEntity<ExampleResponse> getExample() {
    //     return ResponseEntity.ok(new ExampleResponse("Hello, World!"));
    // }



}
