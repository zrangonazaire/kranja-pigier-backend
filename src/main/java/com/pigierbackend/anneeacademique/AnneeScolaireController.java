package com.pigierbackend.anneeacademique;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/annees-academiques")
@RequiredArgsConstructor
public class AnneeScolaireController {
    final AnneeScolaireService anneeScolaireService;
    // Define endpoints for saving and retrieving academic years here
    // Example:
    @PostMapping (value="/save-annee-scolaire",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnneeScolaireDto> saveAnneeScolaire(@RequestBody AnneeScolaireDto anneeScolaireDto) {
        AnneeScolaireDto savedAnnee = anneeScolaireService
            .saveAnneeScolaire(anneeScolaireDto);
        return ResponseEntity.ok(savedAnnee);
    }

    @GetMapping(value="/liste-annees-scolaires",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnneeScolaireDto>> getListeAnneesScolaires() {
        List<AnneeScolaireDto> annees = anneeScolaireService.getAllAnneesScolaires();
        return ResponseEntity.ok(annees);
    }
       


}
