package com.pigierbackend.preinscriptionyakro;

import java.util.List;

public interface PreinscriptionYakroService {
    List<PreinscriptionYakroResponseDto> getAllPreinscriptionYakro();

    PreinscriptionYakroResponseDto getPreinscriptionYakroById(Long id);

    PreinscriptionYakroResponseDto createOrUpdatePreinscriptionYakro(
            PreinscriptionYakroRequestDto preinscriptionYakroRequestDto);

    Boolean deletePreinscriptionYakro(Long id);

    List<PreinscriptionYakroResponseDto> getPreinscriptionYakroByNomEleve(String nomEleve);

}
