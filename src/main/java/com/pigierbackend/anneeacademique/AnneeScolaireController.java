package com.pigierbackend.anneeacademique;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/annees-academiques")
@RequiredArgsConstructor
public class AnneeScolaireController {
    final AnneeScolaireService anneeScolaireService;
    // Define endpoints for saving and retrieving academic years here
    // Example:
    @PostMapping ("/save-annee-scolaire")
    public ResponseEntity<AnneeScolaireDto> saveAnneeScolaire(@RequestBody AnneeScolaireDto anneeScolaireDto) {
        AnneeScolaireDto savedAnnee = anneeScolaireService
            .saveAnneeScolaire(anneeScolaireDto);
        return ResponseEntity.ok(savedAnnee);
    }

    @GetMapping("/liste-annees-scolaires")
    public ResponseEntity<List<AnneeScolaireDto>> getListeAnneesScolaires() {
        List<AnneeScolaireDto> annees = anneeScolaireService.getAllAnneesScolaires();
        return ResponseEntity.ok(annees);
    }
       


}
