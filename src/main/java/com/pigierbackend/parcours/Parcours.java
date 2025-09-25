package com.pigierbackend.parcours;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PARCOURS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parcours {

    @Id
    @Column(name = "CodParc", length = 9, nullable = false)
    private String codParc;

    @Column(name = "LibParc", length = 100)
    private String libParc;

    // // Relation avec Grade
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "CodeG", referencedColumnName = "CodeG", foreignKey = @ForeignKey(name = "FK_PARCOURS_GRADE"))
    // private Grade grade;

    // // Relation avec Vocation
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "CodVoc", referencedColumnName = "CodVoc", foreignKey = @ForeignKey(name = "FK_PARCOURS_VOCATION"))
    // private Vocation vocation;

    // @Column(name = "NivMax")
    // private Integer nivMax;

    // @Column(name = "NivMim")
    // private Integer nivMim;

    // // Relation avec Specialite
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "CodSpecia", referencedColumnName = "CodSpecia", foreignKey = @ForeignKey(name = "FK_PARCOURS_SPECIALITE"))
    // private Specialite specialite;

    @Column(name = "Num_ordre", length = 2)
    private String numOrdre;
}
