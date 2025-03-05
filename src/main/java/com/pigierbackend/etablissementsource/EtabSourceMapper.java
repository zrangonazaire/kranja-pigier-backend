package com.pigierbackend.etablissementsource;

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
public class EtabSourceMapper {
    public EtabSourceResponseDto fromEtabSource(EtabSource etabSource) {
        EtabSourceResponseDto etabSourceResponseDto = new EtabSourceResponseDto();
        BeanUtils.copyProperties(etabSource, etabSourceResponseDto);
        return etabSourceResponseDto;
    }

}
