package com.pigierbackend.preinscriptionyakro;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreinscriptionYakroServiceImpl implements PreinscriptionYakroService {

    final PreinscriptionYakroRepository preinscriptionYakroRepository;
    final PreinscriptionYakroMapper preinscriptionYakroMapper;

    @Override
    public List<PreinscriptionYakroResponseDto> getAllPreinscriptionYakro() {
        return preinscriptionYakroRepository.findAll()
                .stream()
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public PreinscriptionYakroResponseDto getPreinscriptionYakroById(Long id) {
        return preinscriptionYakroRepository.findById(id)
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .orElseThrow(() -> new IllegalArgumentException("La péinscription n'a pas été trouvée"));
    }

    @Override
    public PreinscriptionYakroResponseDto createOrUpdatePreinscriptionYakro(
            PreinscriptionYakroRequestDto preinscriptionYakroRequestDto) {
        PREINSCRIPTIONYAKRO preinscriptionyakro = preinscriptionYakroRepository
                .findById(preinscriptionYakroRequestDto.getId()).orElse(new PREINSCRIPTIONYAKRO());
        BeanUtils.copyProperties(preinscriptionYakroRequestDto, preinscriptionyakro);
        preinscriptionYakroRepository.save(preinscriptionyakro);
        return preinscriptionYakroMapper.fromPreinscriptionYakro(preinscriptionyakro);
    }

    @Override
    public Boolean deletePreinscriptionYakro(Long id) {
        preinscriptionYakroRepository.deleteById(id);
        return !preinscriptionYakroRepository.existsById(id);
    }

    @Override
    public List<PreinscriptionYakroResponseDto> getPreinscriptionYakroByNomEleve(String nomEleve) {
        return preinscriptionYakroRepository.findAll()
        .stream()
        .filter(x->x.getNomprenoms().contains(nomEleve))
        .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
        .collect(Collectors.toList());
    }

}
