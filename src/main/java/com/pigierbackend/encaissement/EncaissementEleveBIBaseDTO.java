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
    private String niveau;
    private String filiere;
    private Integer montantAttendu;
    private Integer montantEncaisse;
    private Integer solde;
}
