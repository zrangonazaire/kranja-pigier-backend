package com.pigierbackend.objetinsc;

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
@Table(name = "objet_Insc")
@Data
@Builder
public class ObjetInsc {
 @Id
    @Column(name = "id_objet", length = 1, nullable = false)
    private String idObjet;

    @Column(name = "nom_objet", length = 50)
    private String nomObjet;
    @OneToMany(mappedBy = "objetInsc")
    private List<EncaissementElevePL> encaissements;
}
