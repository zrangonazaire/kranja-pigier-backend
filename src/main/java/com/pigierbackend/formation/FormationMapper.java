package com.pigierbackend.formation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormationMapper {
    public FormationResponseDto fromFormation(FORMATION formation) {
        FormationResponseDto formationResponseDto = new FormationResponseDto();
        formationResponseDto.setId(formation.getId());
        formationResponseDto.setNomFormation(formation.getNomFormation());
        return formationResponseDto;
    }

}
