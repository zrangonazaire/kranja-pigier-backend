package com.pigierbackend.etatetudiantnew;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"Elèves\"") // Ou la table principale
public class Etudiant {

    @Id
    @Column(name = "\"Matri_Elev\"")
    private String matriElev;

    @Column(name = "\"Nom_Elev\"")
    private String nomElev;

    @Column(name = "\"Lieunais_Elev\"")
    private String lieunaisElev;

    @Column(name = "\"Datenais_Elev\"")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datenaisElev;

    @Column(name = "\"Sexe_Elev\"")
    private String sexeElev;

    @Column(name = "celetud")
    private String celetud;

    @Column(name = "\"Des_Nat\"")
    private String desNat;

    @Column(name = "\"Code_Detcla\"")
    private String codeDetcla;

    // Constructeurs
    public Etudiant() {}

    // Getters et Setters
    public String getMatriElev() { return matriElev; }
    public void setMatriElev(String matriElev) { this.matriElev = matriElev; }

    public String getNomElev() { return nomElev; }
    public void setNomElev(String nomElev) { this.nomElev = nomElev; }

    public String getLieunaisElev() { return lieunaisElev; }
    public void setLieunaisElev(String lieunaisElev) { this.lieunaisElev = lieunaisElev; }

    public Date getDatenaisElev() { return datenaisElev; }
    public void setDatenaisElev(Date datenaisElev) { this.datenaisElev = datenaisElev; }

    public String getSexeElev() { return sexeElev; }
    public void setSexeElev(String sexeElev) { this.sexeElev = sexeElev; }

    public String getCeletud() { return celetud; }
    public void setCeletud(String celetud) { this.celetud = celetud; }

    public String getDesNat() { return desNat; }
    public void setDesNat(String desNat) { this.desNat = desNat; }

    public String getCodeDetcla() { return codeDetcla; }
    public void setCodeDetcla(String codeDetcla) { this.codeDetcla = codeDetcla; }
}