package com.pigierbackend.encaissement;

import java.util.Date;
import jakarta.persistence.ForeignKey;
import com.pigierbackend.eleves.ELEVE;
import com.pigierbackend.folio.FolioPL;
import com.pigierbackend.motifencaissement.MotifEncaissement;
import com.pigierbackend.objetinsc.ObjetInsc;
import com.pigierbackend.reglement.Reglement;
import com.pigierbackend.timbre.Timbre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "Encaissements des El\u00e8ves Pl")
@Data
@Builder
public class EncaissementElevePL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Num_Encais")
    private Integer numEncais;

    @Column(name = "Num_Fol", nullable = false)
    private Integer numFol;

    @Column(name = "Date_Encais")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEncais;

    @Column(name = "Objet_Encais", length = 1)
    private String objetEncais;

    @Column(name = "Matri_Elev", nullable = false, length = 10)
    private String matriElev;

    @Column(name = "Mode_Reg", length = 1)
    private String modeReg;

    @Column(name = "Ancien_Sold")
    private Integer ancienSold;

    @Column(name = "Montant_Encais")
    private Integer montantEncais;

    @Column(name = "Montant_Encais_Ste")
    private Integer montantEncaisSte;

    @Column(name = "Nouveau_Sold")
    private Integer nouveauSold;

    @Column(name = "CodeMotifEnc", columnDefinition = "NCHAR(1)")
    private String codeMotifEnc;

    @Column(name = "caissiaire")
    private Short caissiaire;

    @Column(name = "CodeTarifExam")
    private Integer codeTarifExam;

    @Column(name = "CodeTarif")
    private Integer codeTarif;

    @Column(name = "CodeTarifSte")
    private Integer codeTarifSte;

    @Column(name = "cotisation")
    private Integer cotisation;

    @Column(name = "id_caissiaire", nullable = false)
    private Integer idCaissiaire;

    @Column(name = "NumBord", length = 20)
    private String numBord;

    @Column(name = "fraisExamen")
    private Integer fraisExamen;

    @Column(name = "reservation", length = 1)
    private String reservation;

    @Column(name = "Ancien_Sold_Frais_Exam")
    private Integer ancienSoldFraisExam;

    @Column(name = "Nouveau_Sold_Frais_Exam")
    private Integer nouveauSoldFraisExam;

    @Column(name = "Code_Ste")
    private Integer codeSte;

    @Column(name = "Ancien_Sold_Ste")
    private Integer ancienSoldSte;

    @Column(name = "Nouveau_Sold_Ste")
    private Integer nouveauSoldSte;

    @Column(name = "Ref_Trans_Mobile", length = 50)
    private String refTransMobile;

    @Column(name = "Tel_Trans_Mobile", length = 8)
    private String telTransMobile;

    @Column(name = "Date_Trans_Mobile")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTransMobile;

    @Column(name = "Regularisation", length = 50)
    private String regularisation;

    @Column(name = "Solde_save")
    private Integer soldeSave;

    @Column(name = "NumGuichet", length = 5)
    private String numGuichet;

    @Column(name = "anneeScolEncaissElevPL", length = 10)
    private String anneeScolEncaissElevPL;

    @Column(name = "CodeAcces")
    private Integer codeAcces;

    @Column(name = "CodeUE", length = 25)
    private String codeUE;

    @Column(name = "Montant_Anc_Access")
    private Integer montantAncAccess;

    @Column(name = "Ancien_Solde_Access")
    private Integer ancienSoldeAccess;

    @Column(name = "Nouveau_Sold_Access")
    private Integer nouveauSoldAccess;

    @Column(name = "Nom_Prenoms_Eleve", length = 200)
    private String nomPrenomsEleve;

    @Column(name = "Etablissement_Source", length = 25)
    private String etablissementSource;

    @Column(name = "Classe_Eleve", length = 10)
    private String classeEleve;

    @Column(name = "Montant_Accessoire_Due")
    private Integer montantAccessoireDue;

    @Column(name = "codeCarteBancaire", length = 5)
    private String codeCarteBancaire;

    @Column(name = "numAutorisationBancaire", length = 15)
    private String numAutorisationBancaire;

    @Column(name = "droitinscription")
    private Integer droitInscription;
    @ManyToOne
    @JoinColumn(name = "Matri_Elev", referencedColumnName = "Matri_Elev", insertable = false, updatable = false)
    private ELEVE eleve;

    @ManyToOne
    @JoinColumn(
            name = "Num_Fol",
            referencedColumnName = "Num_Fol",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "FK_Encaissements des El\u00e8ves Pl_Folios Pl"))
    private FolioPL folio;

    @ManyToOne
    @JoinColumn(name = "Mode_Reg", referencedColumnName = "Code_Reg", insertable = false, updatable = false)
    private Reglement reglement;


    @Column(name = "CodeMotifEnc", columnDefinition = "NCHAR(1)", insertable = false, updatable = false)
    private String motifEncaissement;

    @ManyToOne
    @JoinColumn(name = "Objet_Encais", referencedColumnName = "id_objet", insertable = false, updatable = false)
    private ObjetInsc objetInsc;

    @ManyToOne
    @JoinColumn(name = "CodeTarif", referencedColumnName = "CodeTarif", insertable = false, updatable = false)
    private Timbre timbre;
}


