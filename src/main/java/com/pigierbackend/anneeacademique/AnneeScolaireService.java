package com.pigierbackend.anneeacademique;

import java.util.List;

public interface AnneeScolaireService {

    AnneeScolaireDto saveAnneeScolaire(AnneeScolaireDto anneeScolaireDto);
    List<AnneeScolaireDto> getAllAnneesScolaires();

}
