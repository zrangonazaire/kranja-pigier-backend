package com.pigierbackend.encaissement;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EncaissementDTO {
private BigDecimal sommeMontantEncais;
    private int mois;
    private int annee;
    private int trimestre;
    private String niveauLibelle;
}
