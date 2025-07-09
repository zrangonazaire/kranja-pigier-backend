package com.pigierbackend.formation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class FormationServiceImplTest {

    // @Mock
    // private FormationRepository formationRepository;

    // @Mock
    // private FormationMapper formationMapper;

    // @InjectMocks
    // private FormationServiceImpl formationServiceImpl;

    // @BeforeEach
    // void setUp() {
    //     MockitoAnnotations.openMocks(this);
    // }

    // @Test
    // void testCreateOrUpdateFormation_NewFormation() {
    //     FormationRequestDto dto = new FormationRequestDto();
    //     dto.setId(null);
    //     dto.setNomFormation("New Formation");

    //     FORMATION formation = new FORMATION();
    //     formation.setNomFormation("New Formation");

    //     when(formationRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    //     when(formationRepository.save(any(FORMATION.class))).thenReturn(formation);
    //     when(formationMapper.fromFormation(any(FORMATION.class))).thenReturn(new FormationResponseDto());

    //     assertEquals("New Formation", formation.getNomFormation());
    // }

    // @Test
    // void testCreateOrUpdateFormation_UpdateFormation() {
    //     FormationRequestDto dto = new FormationRequestDto();
    //     dto.setId(1L);
    //     dto.setNomFormation("Updated Formation");

    //     FORMATION formation = new FORMATION();
    //     formation.setNomFormation("Old Formation");

    //     when(formationRepository.findById(1L)).thenReturn(Optional.of(formation));
    //     when(formationRepository.save(any(FORMATION.class))).thenReturn(formation);
    //     when(formationMapper.fromFormation(any(FORMATION.class))).thenReturn(new FormationResponseDto());

    //     assertEquals("Updated Formation", formation.getNomFormation());
    // }
}