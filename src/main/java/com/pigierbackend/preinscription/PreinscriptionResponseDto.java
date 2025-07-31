package com.pigierbackend.preinscription;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreinscriptionResponseDto {
  private String id;
  @NotBlank(message = "Saisie obligatoire.")
  private String nomPrenoms;
  @NotBlank(message = "Saisie obligatoire.")
  private String dateNaissance;

  private String lieuNaissance;
  @NotBlank(message = "Saisie obligatoire.")
  private String sexe;

  private String nationalite;
  @NotBlank(message = "Saisie obligatoire.")
  private String nationaliteIdentite;
  @NotBlank(message = "Saisie obligatoire.")
  private String numeroIdentite;

  private String telephoneEtudiant;
  @NotEmpty(message = "Saisie obligatoire.")
  @NotBlank(message = "Saisie obligatoire.")
  @NotNull(message = "Saisie obligatoire.")
  private String cellulaireEtudiant;

  private String emailEtudiant;

  private String villeEtudiant;

  private String communeEtudiant;

  private String baccalaureat;

  private String anneeBac;

  private String diplomeEquivalence;

  private String anneeDiplomeEquivalence;

  private String niveauEtudes;

  private String anneeNiveauEtudes;

  private String grade;

  private String anneeGrade;

  private String specialiteGrade;

  private String etablissementFrequente;
  @NotBlank(message = "Saisie obligatoire.")
  private String formationSouhaitee;

  private String idPermanent;

  private String nomPere;

  private String nomMere;

  private String titreResponsable;

  private String responsable;

  private String nomResponsable;

  private String professionResponsable;

  private String employeurResponsable;

  private String villeResponsable;

  private String communeResponsable;

  private String boitePostaleResponsable;

  private String cellulaireResponsable;

  private String telephoneBureauResponsable;

  private String telephoneDomicileResponsable;

  private String emailResponsable;

  private String copieBac;

  private String copieDernierDiplome;

  private String contactNomPrenom1;

  private String contactAdresse1;

  private String contactTelephone1;

  private String contactCellulaire1;

  private String contactNomPrenom2;

  private String contactAdresse2;

  private String contactTelephone2;

  private String contactCellulaire2;

  private String cliniqueDeclaree;

  private String nomClinique;

  private String telephoneClinique;

  private String medecinClinique;

  private String contactMedecin;

  private String maladies;

  private String soins;

  private String medicaments;

  private String premiersSoins;

  private String interventionsChirurgicales;

  private String decision;

  private String numeroTable;

  private String numeroMatricule;

  private Integer totalBac;

  private String matierePrincipale;

  private String anneeScolaire;

  private String etablissementSource;

  private Boolean inscritSousTitre;
  String utilisateurCreateur;
  private LocalDateTime dateInscription;
  
}
