package com.pigierbackend.formation;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormationServiceImpl implements FormationService {
    final FormationRepository formationRepository;
    final FormationMapper formationMapper;

    @Override
    public FormationResponseDto createOrUpdateFormation(FormationRequestDto dto) {
        FORMATION formation = formationRepository.findById(dto.getId()).orElse(new FORMATION());
        formation.setNomFormation(dto.getNomFormation());
        formationRepository.save(formation);
        return formationMapper.fromFormation(formation);
    }

    @Override
    public Boolean deleteFormation(Long id) {
        FORMATION formation = formationRepository.findById(id).orElse(null);
        if (formation != null) {
            formationRepository.delete(formation);
            return true;
        }
        return false;
    }

    @Override
    public List<FormationResponseDto> findAllFormation() {
        return formationRepository.findAll()
                .stream()
                .distinct()
                .sorted(Comparator.comparing(FORMATION::getNomFormation))
                .map(formationMapper::fromFormation)
                .toList();
    }

    @Override
    public FormationResponseDto findFormationById(Long id) {
       FORMATION formation = formationRepository.findById(id).orElse(null);
       if (formation != null) {
           return formationMapper.fromFormation(formation);         
        }
        return null;
    }

}
