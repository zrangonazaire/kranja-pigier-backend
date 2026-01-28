package com.pigierbackend.administration;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministrationTempoDto {

    Long id;
    String matriElev;
    String nomPrenoms;
    String groupe;
    String codeUE;
    Double moyenneGle;
    String decision;
    AdministrationEnum traitement;
}
