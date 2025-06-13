package com.pigierbackend.eleves;

import java.time.LocalDate;

import com.pigierbackend.abstractentity.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "Elèves")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ELEVE {
    // @Column(name = "matricule", nullable = false, length = 10, unique = true)
    // String matricule;
    // String nomprenom;
    // @Column(name = "sexe", nullable = false, length = 1)
    // String sexe;

    // @Column(name = "role") // User role
    // String role;
    @Column(name = "matricule")
    @Id
    private String matriElev;
    private String codeGrp;
    private String nomElev;
    private String sexeElev;
    private String codeSte;
    private String codeEta;
    private String subEta;
    private String codeReg;
    private String codeBrs;
    private String codeOpt;
    private Double montOpt;
    private String codeDetcla;
    private String nomCla;
    private String codeNat;
    private String lieunaisElev;
    private LocalDate datenaisElev; // Supposons que c'est juste une date
    private String telBurRespElev;
    private String actenaisElev;
    private String nomPereElev;
    private String nomMereElev;
    private String etablOrigElev;
    private String classOrigElev;
    private String anneeScoOrig;
    private LocalDate dateEntreElev; // Supposons que c'est juste une date
    private String codeNiv;
    private String anneeScoElev;
    private String serieBacElev;
    private LocalDate dateObtBacElev; // Supposons que c'est juste une date
    private Boolean redoubleElev;
    private String cycleElev;
    private String nomRespElev;
    private String profRespElev;
    private String titreRespElev;
    private String adresRespElev;
    private String villeRespElev;
    private String telDomRespElev;
    private Double montantScoElev;
    private Double soldScoElev;
    private Double remiseElev;
    private Double bourseElev;
    private Double soldBourseElev;
    private Boolean dejaRegle;
    private byte[] photo; // Pour stocker les données binaires d'une image
    private String comment;
    private String conditionElev;
    private Double cotisation;
    private Double soldFraisExam;
    private String prenomElev;
    private Double scolFDFP;
    private String idFDFP;
    private String idPreinscription;
    private Boolean reservation;
    private String numtabl;
    private String numatri;
    private String totbac;
    private String matpc;
    private String celetud;
    private String teletud;
    private String villetud;
    private String cometud;
    private String mailetud;
    private Double reduction;
    private String etabSource;
    private Double avoir;
    private String natPiece;
    private Double montantExamen;
    private Boolean admissionAnneeSup;
    private Boolean cloturePeda;
    private Boolean extraitNaissance;
    private Boolean photocopieDiplomes;
    private Boolean photocopieLegaliseBAC;
    private Boolean photocopieBulletins;
    private Boolean photoIdentite;
    private Boolean ficheDemandeInscription;
    private Boolean ficheMedicale;
    private Boolean cinqEnveloppes;
    private Boolean cinqTimbres;
    private Boolean deuxiemeExtraitNaissance;
    private Boolean deuxiemePhotocopieLegaliseBAC;
    private LocalDate dateInscriEleve; // Supposons que c'est juste une date
    private String mailparent;
    private Boolean inscritCarte;
    private String idperm;
    private Boolean inscritSousTitre;
    private Boolean inscritSousBulletin;
    private String univmetiers;
    private String mdpunivmetier;
    private Double droitinscription;
}
