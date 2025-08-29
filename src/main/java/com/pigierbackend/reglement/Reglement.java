package com.pigierbackend.reglement;

import java.util.List;

import com.pigierbackend.encaissement.EncaissementElevePL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "Règlements")
@Builder
@Data
public class Reglement {
    @Id
    @Column(name = "Code_Reg", length = 1, nullable = false)
    private String codeReg;

    @Column(name = "Des_Reg", length = 50)
    private String desReg;
    @OneToMany(mappedBy = "reglement")
    private List<EncaissementElevePL> encaissements;
}
