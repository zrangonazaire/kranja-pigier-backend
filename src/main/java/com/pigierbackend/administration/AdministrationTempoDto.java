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
    String ecue1;
    String ecue2;
    String dteDeliber;
    Double moyenneCC;
    Double moyenneExam;
    Double moyenneGle;
    String decision;
    String annee;
    String dateOperation;
    Integer creditUE;
    String classActu;
    String classExam;
    AdministrationEnum traitement;
}
