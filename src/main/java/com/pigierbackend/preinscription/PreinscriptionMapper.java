package com.pigierbackend.preinscription;

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
public  class PreinscriptionMapper {
   public PreinscriptionResponseDto fromPreinscriptionYakro(PREINSCRIPTION pre){
        PreinscriptionResponseDto prDto=new PreinscriptionResponseDto();
        BeanUtils.copyProperties(pre,prDto);
        return prDto;
    };
}
