package com.pigierbackend.etatetudiant;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Entity
@Table(name = "vw_Elèves_Filtres")
public class Eleve {
    
    @Id
    @Column(name = "Matri_Elev")
    private String matriElev;
    
    @Column(name = "DateInscri_Eleve")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInscriEleve;
    
    @Column(name = "Nom_Elev")
    private String nomElev;
    
    @Column(name = "celetud")
    private String celEtud;
    
    @Column(name = "univmetiers")
    private String univMetiers;
    
    @Column(name = "AnneeSco_Elev")
    private String anneeScoElev;
    
    @Column(name = "Etab_source")
    private String etabSource;
    
    @Column(name = "Code_Detcla")
    private String codeDetcla;
    
    @Column(name = "Niveau")
    private String niveau;
    
    // Getters and Setters
    public String getMatriElev() { return matriElev; }
    public void setMatriElev(String matriElev) { this.matriElev = matriElev; }
    
    public Date getDateInscriEleve() { return dateInscriEleve; }
    public void setDateInscriEleve(Date dateInscriEleve) { this.dateInscriEleve = dateInscriEleve; }
    
    public String getNomElev() { return nomElev; }
    public void setNomElev(String nomElev) { this.nomElev = nomElev; }
    
    public String getCelEtud() { return celEtud; }
    public void setCelEtud(String celEtud) { this.celEtud = celEtud; }
    
    public String getUnivMetiers() { return univMetiers; }
    public void setUnivMetiers(String univMetiers) { this.univMetiers = univMetiers; }
    
    public String getAnneeScoElev() { return anneeScoElev; }
    public void setAnneeScoElev(String anneeScoElev) { this.anneeScoElev = anneeScoElev; }
    
    public String getEtabSource() { return etabSource; }
    public void setEtabSource(String etabSource) { this.etabSource = etabSource; }
    
    public String getCodeDetcla() { return codeDetcla; }
    public void setCodeDetcla(String codeDetcla) { this.codeDetcla = codeDetcla; }
    
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
}