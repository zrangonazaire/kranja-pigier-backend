package com.pigierbackend.specialte;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SPECIALITE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specialite {

    @Id
    @Column(name = "CodSpecia", length = 25, nullable = false)
    private String codSpecia;

    @Column(name = "LibSpecia", length = 100)
    private String libSpecia;

    // Relation avec MEN (si table MEN existe)
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "CodMen", referencedColumnName = "CodMen")
    // private Men men;
}
