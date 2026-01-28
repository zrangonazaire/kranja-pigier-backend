package com.pigierbackend.encaissement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncaissementEleveBIBaseDTO {

    private String matricule;
    private String nomPrenom;

    private String niveau;      // BTS / LICENCE / MASTER
    private String filiere;     // nomCla ou codeDetcla

    private Integer montantAttendu;     // scolarité + droits + cotisation + examen
    private Integer montantEncaisse;

    private Integer solde;      // solde global élève
}
