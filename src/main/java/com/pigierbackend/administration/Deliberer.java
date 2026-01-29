package com.pigierbackend.administration;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "DELIBERER",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"IdGroupe", "CodeUE", "Matri_Elev"})
        }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Deliberer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id") // colonne primaire
    Long id;

    @Column(name = "IdGroupe")
    String idGroupe;

    @Column(name = "CodeUE")
    String codeUE;

    @Column(name = "Matri_Elev", nullable = false)
    String matriElev;

    @Column(name = "Des_Deliber")
    String desDeliber;

    @Column(name = "Dte_Deliber")
    String dteDeliber;

    @Column(name = "Moyenne")
    Double moyenne;

    @Column(name = "Annee")
    String annee;

    @Column(name = "Modifier")
    String modifier;

    @Column(name = "path_justificatif")
    String pathJustificatif;

    @Column(name = "date_operation")
    String dateOperation;

    @Column(name = "creditUE")
    Integer creditUE;

    @Column(name = "semestreUe")
    String semestreUe;

    @Column(name = "classActu")
    String classActu;

    @Column(name = "classExam")
    String classExam;

    @Column(name = "nomPrenoms")
    String nomPrenoms;
}


