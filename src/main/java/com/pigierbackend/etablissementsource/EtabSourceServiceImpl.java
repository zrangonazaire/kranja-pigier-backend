package com.pigierbackend.etablissementsource;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        EtabSource etabSource = etabSourceRepository.findById(dto.getId()).orElse(new EtabSource());
        etabSource.setLibEtabSource(dto.getLibEtabSource());
        etabSourceRepository.save(etabSource);
        return etabSourceMapper.fromEtabSource(etabSource);
    }

    @Override
    public Boolean deleteEtabSource(Long id) {
        Optional<EtabSource> etabSourceOpt = etabSourceRepository.findById(id);
        if (etabSourceOpt.isPresent()) {
            etabSourceRepository.delete(etabSourceOpt.get());
            return true;
        }
        return false;
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
