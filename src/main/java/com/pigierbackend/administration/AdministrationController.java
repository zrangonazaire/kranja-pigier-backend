package com.pigierbackend.administration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administration")
@CrossOrigin("*")
public class AdministrationController {

    private final AdministrationService service;

    /** IMPORT */
    @PostMapping("/import")
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        service.importExcel(file);
        return ResponseEntity.ok("Import effectué avec succès");
    }

    /** LISTE */
    @GetMapping("/list")
    public ResponseEntity<List<AdministrationTempoDto>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/traiter")
    public ResponseEntity<String> traiter() {
        service.traiterAdministrationTempo();
        return ResponseEntity.ok("Traitement effectué avec succès");
    }

}
