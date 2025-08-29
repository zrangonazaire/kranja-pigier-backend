package com.pigierbackend.folio;

import java.util.Date;
import java.util.List;

import com.pigierbackend.encaissement.EncaissementElevePL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "Folios Pl")
@Builder
@Data
public class FolioPL {
    @Id
    @Column(name = "Num_Fol")
    private Integer numFol;

    @Column(name = "Date_Jour")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateJour;

@OneToMany(mappedBy = "folio")
private List<EncaissementElevePL> encaissements;

}
