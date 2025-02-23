package com.pigierbackend.etablissementsource;

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
public class EtabSourceServiceImpl implements EtabSourceService {
    final EtabSourceRepository etabSourceRepository;
    final EtabSourceMapper etabSourceMapper;

    @Override
    public EtabSourceResponseDto creatOrUpdateEtabSource(EtabSourceRequestDto dto) {
        EtabSource etabSource = etabSourceRepository.findById(dto.getId()).orElse(null);
        EtabSourceResponseDto etabdto = new EtabSourceResponseDto();
        if (etabSource != null) {
            etabSource.setLibEtabSource(dto.getLibEtabSource());
            etabSourceRepository.save(etabSource);
            etabdto.setId(dto.getId());
            etabdto.setLibEtabSource(dto.getLibEtabSource());
        } else {
            etabdto.setId(dto.getId());
            etabdto.setLibEtabSource(dto.getLibEtabSource());
        }
        return etabdto;
    }

    @Override
    public Boolean deleteEtabSource(Long id) {
        EtabSource etabSource = etabSourceRepository.findById(id).orElse(null);
        if (etabSource == null) {
            return false;
        }
        etabSourceRepository.delete(etabSource);
        return true;
    }

    @Override
    public List<EtabSourceResponseDto> findAllEtabSour() { 
       
        return etabSourceRepository.findAll()
        .stream()
        .distinct()
        .sorted(Comparator.comparing(EtabSource::getLibEtabSource))
        .map(etabSourceMapper::fromEtabSource)
        .toList();
    }
}
