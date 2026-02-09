package com.pigierbackend.encaissement;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardEncaissementBIDTO {

    private Long totalEleves;
    private Long totalElevesSoldes;
    private Long totalElevesNonSoldes;
    private Integer montantTotalAttendu;
    private Integer montantTotalEncaisse;
    private Integer montantTotalRestant;
    private Integer montantDuAuxEleves;
    private Double tauxElevesSoldes;
    private Double tauxRecouvrement;
    private Map<String, Integer> encaissementParNiveau;
    private Map<String, Integer> encaissementParFiliere;
    private List<EncaissementEleveBIBaseDTO> details;
}

