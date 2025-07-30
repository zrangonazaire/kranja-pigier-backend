package com.pigierbackend.preinscription;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Table(name = "preinscription")
@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PREINSCRIPTION {
        @Id
    @Column(name = "id", length = 12, nullable = false)
    private String id;

    @Column(name = "nomprenoms", length = 150)
    private String nomPrenoms;

    @Column(name = "datnais")
    private LocalDateTime dateNaissance;

    @Column(name = "lieunais", length = 100)
    private String lieuNaissance;

    @Column(name = "sexe", length = 1)
    private String sexe;

    @Column(name = "nationalite", length = 50)
    private String nationalite;

    @Column(name = "natident", length = 30)
    private String nationaliteIdentite;

    @Column(name = "numidentite", length = 50)
    private String numeroIdentite;

    @Column(name = "teletud", length = 30)
    private String telephoneEtudiant;

    @Column(name = "celetud", length = 30)
    private String cellulaireEtudiant;

    @Column(name = "emailetud", length = 100)
    private String emailEtudiant;

    @Column(name = "viletud", length = 150)
    private String villeEtudiant;

    @Column(name = "cometud", length = 150)
    private String communeEtudiant;

    @Column(name = "baccalaureat", length = 3)
    private String baccalaureat;

    @Column(name = "annbac", length = 5)
    private String anneeBac;

    @Column(name = "diplequiv", length = 150)
    private String diplomeEquivalence;

    @Column(name = "anndiplequiv", length = 5)
    private String anneeDiplomeEquivalence;

    @Column(name = "nivoetud", length = 100)
    private String niveauEtudes;

    @Column(name = "annivoetud", length = 10)
    private String anneeNiveauEtudes;

    @Column(name = "grade", length = 100)
    private String grade;

    @Column(name = "anngrad", length = 10)
    private String anneeGrade;

    @Column(name = "specgrad", length = 100)
    private String specialiteGrade;

    @Column(name = "etsfreq", length = 150)
    private String etablissementFrequente;

    @Column(name = "formsouh", length = 200)
    private String formationSouhaitee;

    @Column(name = "idperm", length = 20)
    private String idPermanent;

    @Column(name = "nompere", length = 150)
    private String nomPere;

    @Column(name = "nomere", length = 150)
    private String nomMere;

    @Column(name = "titrespo", length = 10)
    private String titreResponsable;

    @Column(name = "respo", length = 10)
    private String responsable;

    @Column(name = "nomrespo", length = 150)
    private String nomResponsable;

    @Column(name = "profrespo", length = 100)
    private String professionResponsable;

    @Column(name = "emprespo", length = 150)
    private String employeurResponsable;

    @Column(name = "vilrespo", length = 150)
    private String villeResponsable;

    @Column(name = "comrespo", length = 150)
    private String communeResponsable;

    @Column(name = "bprespo", length = 50)
    private String boitePostaleResponsable;

    @Column(name = "celrespo", length = 30)
    private String cellulaireResponsable;

    @Column(name = "telburespo", length = 30)
    private String telephoneBureauResponsable;

    @Column(name = "teldomrespo", length = 30)
    private String telephoneDomicileResponsable;

    @Column(name = "emailrespo", length = 100)
    private String emailResponsable;

    @Column(name = "copiebac", length = 150)
    private String copieBac;

    @Column(name = "copderndipl", length = 150)
    private String copieDernierDiplome;

    @Column(name = "contnompren1", length = 150)
    private String contactNomPrenom1;

    @Column(name = "contadr1", length = 100)
    private String contactAdresse1;

    @Column(name = "contel1", length = 30)
    private String contactTelephone1;

    @Column(name = "contcel1", length = 30)
    private String contactCellulaire1;

    @Column(name = "contnompren2", length = 150)
    private String contactNomPrenom2;

    @Column(name = "contadr2", length = 100)
    private String contactAdresse2;

    @Column(name = "contel2", length = 30)
    private String contactTelephone2;

    @Column(name = "contcel2", length = 30)
    private String contactCellulaire2;

    @Column(name = "clindec", length = 5)
    private String cliniqueDeclaree;

    @Column(name = "clinnom", length = 100)
    private String nomClinique;

    @Column(name = "clintel", length = 30)
    private String telephoneClinique;

    @Column(name = "clinmed", length = 100)
    private String medecinClinique;

    @Column(name = "clinmedcont", length = 30)
    private String contactMedecin;

    @Column(name = "maladies", length = 300)
    private String maladies;

    @Column(name = "soins", length = 300)
    private String soins;

    @Column(name = "medic", length = 300)
    private String medicaments;

    @Column(name = "premsoins", length = 300)
    private String premiersSoins;

    @Column(name = "intervchir", length = 300)
    private String interventionsChirurgicales;

    @Column(name = "datinscrip")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateInscription;

    @Column(name = "decision", length = 3)
    private String decision;

    @Column(name = "numtabl", length = 20)
    private String numeroTable;

    @Column(name = "numatri", length = 20)
    private String numeroMatricule;

    @Column(name = "totbac")
    private Integer totalBac;

    @Column(name = "matpc", length = 20)
    private String matierePrincipale;

    @Column(name = "anneescolaire", length = 10)
    private String anneeScolaire;

    @Column(name = "Etab_source", length = 25)
    private String etablissementSource;

    @Column(name = "Inscrit_Sous_Titre")
    private Boolean inscritSousTitre;
}
