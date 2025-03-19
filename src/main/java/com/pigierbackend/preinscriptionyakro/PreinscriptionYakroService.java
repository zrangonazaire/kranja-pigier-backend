package com.pigierbackend.preinscriptionyakro;

import java.util.List;

public interface PreinscriptionYakroService {
    List<PreinscriptionYakroResponseDto> getAllPreinscriptionYakro(int page, int size);

    PreinscriptionYakroResponseDto getPreinscriptionYakroById(String id);

    PreinscriptionYakroResponseDto createOrUpdatePreinscriptionYakro(
            PreinscriptionYakroRequestDto preinscriptionYakroRequestDto);

    Boolean deletePreinscriptionYakro(String id);

    List<PreinscriptionYakroResponseDto> getPreinscriptionYakroByNomEleve(String nomEleve);

}
