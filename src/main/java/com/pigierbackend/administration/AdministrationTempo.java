package com.pigierbackend.administration;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ADMINISTRATION_TEMPO")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministrationTempo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    AdministrationEnum traitement;
}
