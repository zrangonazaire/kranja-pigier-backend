package com.pigierbackend.etablissementsource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EtabSourceRequestDto {
    Long id;
    @NotBlank(message = "Saisie obligatoire.")
    @NotEmpty(message = "Saisie obligatoire.")
    @NotNull(message = "Saisie obligatoire.")
    String libEtabSource;

}
