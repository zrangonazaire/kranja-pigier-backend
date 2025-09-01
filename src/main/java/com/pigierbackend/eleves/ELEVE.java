package com.pigierbackend.eleves;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.pigierbackend.encaissement.EncaissementElevePL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Elèves")
@Getter
@Setter
public class ELEVE {

    @Id
    @Column(name = "Matri_Elev", length = 10, nullable = false,columnDefinition = "nvarchar(10)")
    private String matriElev;

    @Column(name = "Code_Grp")
    private Short codeGrp;

    @Column(name = "Nom_Elev", length = 50,columnDefinition = "nvarchar(50)")
    private String nomElev;

    @Column(name = "Sexe_Elev", length = 1, columnDefinition = "nvarchar(1)")
    private String sexeElev;

    @Column(name = "Code_Ste")
    private Integer codeSte;

    @Column(name = "Code_Eta")
    private Integer codeEta;

    @Column(name = "Sub_Eta")
    private Integer subEta;

    @Column(name = "Code_Reg", length = 1)
    private String codeReg;

    @Column(name = "Code_Brs")
    private Integer codeBrs;

    @Column(name = "Code_Opt")
    private Integer codeOpt;

    @Column(name = "Mont_Opt", precision = 19, scale = 4)
    private BigDecimal montOpt;

    @Column(name = "Code_Detcla", length = 10)
    private String codeDetcla;

    @Column(name = "Nom_Cla", length = 100)
    private String nomCla;

    @Column(name = "Code_Nat")
    private Integer codeNat;

    @Column(name = "Lieunais_Elev", length = 20)
    private String lieunaisElev;

    @Column(name = "Datenais_Elev")
    private LocalDateTime datenaisElev;

    @Column(name = "TelBurResp_Elev", length = 30)
    private String telBurRespElev;

    @Column(name = "Actenais_Elev", length = 20)
    private String actenaisElev;

    @Column(name = "NomPere_Elev", length = 20)
    private String nomPereElev;

    @Column(name = "NomMere_Elev", length = 20)
    private String nomMereElev;

    @Column(name = "EtablOrig_Elev", length = 25)
    private String etablOrigElev;

    @Column(name = "ClassOrig_Elev", length = 20)
    private String classOrigElev;

    @Column(name = "AnneeSco_Orig", length = 9)
    private String anneeScoOrig;

    @Column(name = "DateEntre_Elev")
    private LocalDateTime dateEntreElev;

    @Column(name = "Code_Niv", nullable = false)
    private Integer codeNiv;

    @Column(name = "AnneeSco_Elev", length = 9)
    private String anneeScoElev;

    @Column(name = "SerieBac_Elev", length = 20)
    private String serieBacElev;

    @Column(name = "DateObtBac_Elev")
    private LocalDateTime dateObtBacElev;

    @Column(name = "Redouble_Elev")
    private Boolean redoubleElev;

    @Column(name = "Cycle_Elev", length = 2)
    private String cycleElev;

    @Column(name = "NomResp_Elev", length = 30)
    private String nomRespElev;

    @Column(name = "ProfResp_Elev", length = 30)
    private String profRespElev;

    @Column(name = "TitreResp_Elev", length = 4)
    private String titreRespElev;

    @Column(name = "AdresResp_Elev", length = 30)
    private String adresRespElev;

    @Column(name = "VilleResp_Elev", length = 15)
    private String villeRespElev;

    @Column(name = "TelDomResp_Elev", length = 30)
    private String telDomRespElev;

    @Column(name = "MontantSco_Elev")
    private Integer montantScoElev;

    @Column(name = "SoldSco_Elev")
    private Integer soldScoElev;

    @Column(name = "Remise_Elev")
    private Float remiseElev;

    @Column(name = "Bourse_Elev")
    private Integer bourseElev;

    @Column(name = "SoldBourse_Elev")
    private Integer soldBourseElev;

    @Column(name = "DejaRegle")
    private Boolean dejaRegle;

    @Lob
    @Column(name = "Photo", columnDefinition = "varbinary(max)")
    private byte[] photo;

    @Lob
    @Column(name = "Comment",columnDefinition = "NVARCHAR(MAX)")
    private String comment;

    @Column(name = "Condition_Elev", length = 50)
    private String conditionElev;

    @Column(name = "Cotisation")
    private Integer cotisation;

    @Column(name = "SoldFraisExam")
    private int soldFraisExam;

    @Column(name = "Prenom_Elev", length = 100)
    private String prenomElev;

    @Column(name = "scolFDFP")
    private int scolFDFP;

    @Column(name = "idFDFP")
    private int idFDFP;

    @Column(name = "idPreinscription", length = 12)
    private String idPreinscription;

    @Column(name = "Reservation",columnDefinition = "nvarchar(10)")
    private String reservation;

    @Column(name = "numtabl", length = 20)
    private String numtabl;

    @Column(name = "numatri", length = 20)
    private String numatri;

    @Column(name = "totbac")
    private int totbac;

    @Column(name = "matpc", length = 20)
    private String matpc;

    @Column(name = "celetud", length = 30)
    private String celetud;

    @Column(name = "teletud", length = 30)
    private String teletud;

    @Column(name = "villetud", length = 50)
    private String villetud;

    @Column(name = "cometud", length = 50)
    private String cometud;

    @Column(name = "mailetud", length = 200)
    private String mailetud;

    @Column(name = "reduction")
    private int reduction;

    @Column(name = "Etab_source", length = 25)
    private String etabSource;

    @Column(name = "Avoir")
    private int avoir;

    @Column(name = "natPiece", length = 150)
    private String natPiece;

    @Column(name = "montantExamen")
    private int montantExamen;

    @Column(name = "Admission_Annee_Sup")
    private Boolean admissionAnneeSup;

    @Column(name = "Cloture_Peda")
    private Boolean cloturePeda;

    @Column(name = "Extrait_naissance")
    private Boolean extraitNaissance;

    @Column(name = "Photocopie_diplômes")
    private Boolean photocopieDiplomes;

    @Column(name = "Photocopie_Legalise_BAC")
    private Boolean photocopieLegaliseBAC;

    @Column(name = "Photocopie_Bulletins")
    private Boolean photocopieBulletins;

    @Column(name = "Photo_identité")
    private Boolean photoIdentite;

    @Column(name = "Fiche_demande_inscription")
    private Boolean ficheDemandeInscription;

    @Column(name = "Fiche_médicale")
    private Boolean ficheMedicale;

    @Column(name = "Cinq_enveloppes")
    private Boolean cinqEnveloppes;

    @Column(name = "Cinq_timbres")
    private Boolean cinqTimbres;

    @Column(name = "Deuxieme_Extrait_naissance")
    private Boolean deuxiemeExtraitNaissance;

    @Column(name = "Deuxieme_Photocopie_Legalise_BAC")
    private Boolean deuxiemePhotocopieLegaliseBAC;

    @Column(name = "DateInscri_Eleve")
    private LocalDateTime dateInscriEleve;

    @Column(name = "mailparent", length = 200)
    private String mailparent;

    @Column(name = "Inscrit_Carte")
    private Boolean inscritCarte;

    @Column(name = "idperm", length = 20)
    private String idperm;

    @Column(name = "Inscrit_Sous_Titre")
    private Boolean inscritSousTitre;

    @Column(name = "Inscrit_Sous_Bulletin")
    private Boolean inscritSousBulletin;

    @Column(name = "univmetiers", length = 250)
    private String univmetiers;

    @Column(name = "mdpunivmetier", length = 250)
    private String mdpunivmetier;

    @Column(name = "droitinscription")
    private int droitinscription;
@OneToMany(mappedBy = "eleve")
    private List<EncaissementElevePL> encaissements;
    // Ajoutez ici les getters et setters
}