package com.pigierbackend.anneeacademique;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Année Scolaire")
@Builder
public class ANNEEACADEMIQUE {
    @Id
    @Column(name = "Annee_Sco", nullable = false, columnDefinition = "varchar(225)")
    String Annee_Sco;
    AnneeScolaireDto toDto() {
        return AnneeScolaireDto.builder()
                .Annee_Sco(this.Annee_Sco)
                .build();
    }
}
