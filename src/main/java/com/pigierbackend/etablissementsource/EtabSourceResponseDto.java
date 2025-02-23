package com.pigierbackend.etablissementsource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EtabSourceResponseDto {
    Long id;
    String libEtabSource;
}
