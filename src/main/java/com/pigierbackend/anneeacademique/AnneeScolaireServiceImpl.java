package com.pigierbackend.anneeacademique;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnneeScolaireServiceImpl implements AnneeScolaireService {

    final AnneeScolaireRepository anneeScolaireRepository;

    @Override
    public AnneeScolaireDto saveAnneeScolaire(AnneeScolaireDto anneeScolaireDto) {
        ANNEEACADEMIQUE anneeAcademique = ANNEEACADEMIQUE.builder()
                .Annee_Sco(anneeScolaireDto.getAnnee_Sco())
                .build();
        ANNEEACADEMIQUE savedAnneeAcademique = anneeScolaireRepository.save(anneeAcademique);
        return savedAnneeAcademique.toDto();
    }

    @Override
    public List<AnneeScolaireDto> getAllAnneesScolaires() {
        // Implementation to retrieve all academic years
        return anneeScolaireRepository.findAll().stream()
                .map(ANNEEACADEMIQUE::toDto)
                .sorted(Comparator.comparing(AnneeScolaireDto::getAnnee_Sco).reversed())
                .toList();
    }

}
