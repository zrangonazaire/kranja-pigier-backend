package com.pigierbackend.formation;

import java.util.List;

public interface FormationService {
FormationResponseDto createOrUpdateFormation(FormationRequestDto dto);
Boolean deleteFormation(Long id);
List<FormationResponseDto> findAllFormation();
FormationResponseDto findFormationById(Long id);
}
