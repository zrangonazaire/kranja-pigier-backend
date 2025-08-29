package com.pigierbackend.timbre;

import java.math.BigDecimal;
import java.util.List;

import com.pigierbackend.encaissement.EncaissementElevePL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Builder;

@Entity
@Table(name = "Timbre")
@Builder
@Data
public class Timbre {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeTarif")
    private Integer codeTarif;

    @Column(name = "TrancheTarif", length = 40)
    private String trancheTarif;

    @Column(name = "Frais", precision = 19, scale = 4)
    private BigDecimal frais;
    @OneToMany(mappedBy = "timbre")
    private List<EncaissementElevePL> encaissements;
}
