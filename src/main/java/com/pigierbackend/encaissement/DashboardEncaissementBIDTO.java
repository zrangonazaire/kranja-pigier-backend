package com.pigierbackend.encaissement;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardEncaissementBIDTO {

    // =======================
    // CARTES (PI CARDS)
    // =======================
    private Long totalEleves;

    private Long totalElevesSoldes;
    private Long totalElevesNonSoldes;

    private Integer montantTotalAttendu;
    private Integer montantTotalEncaisse;
    private Integer montantTotalRestant;

    private Integer montantDuAuxEleves; // solde < 0

    // =======================
    // TAUX
    // =======================
    private Double tauxElevesSoldes;
    private Double tauxRecouvrement;

    // =======================
    // DONNEES CAMEMBERTS
    // =======================
    private Map<String, Integer> encaissementParNiveau;
    private Map<String, Integer> encaissementParFiliere;

    // =======================
    // DETAIL (optionnel)
    // =======================
    private List<EncaissementEleveBIBaseDTO> details;
}

