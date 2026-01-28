package com.pigierbackend.administration;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "DELIBERER",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"codeUE", "idGroupe"})
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
    Long id;

    String idGroupe;
    String codeUE;
    String desDeliber;
    String dteDeliber;
    Double moyenne;
    String matriElev;
    String annee;
    String modifier;
    String pathJustificatif;
    String dateOperation;
    Integer creditUE;
    String semestreUe;
    String classActu;
    String classExam;
    String nomPrenoms;
}
