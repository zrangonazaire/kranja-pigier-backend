package com.pigierbackend.etatetudiantnew;

import java.util.Date;

public class EtudiantDTO {
    private String matriElev;
    private String nomElev;
    private String lieunaisElev;
    private Date datenaisElev;
    private String sexeElev;
    private String celetud;
    private String desNat;
    private String codeDetcla;

    // Constructeur à partir du résultat Object[] avec gestion sécurisée des types
    public EtudiantDTO(Object[] result) {
        this.matriElev = convertToString(result[0]);
        this.nomElev = convertToString(result[1]);
        this.lieunaisElev = convertToString(result[2]);
        this.datenaisElev = convertToDate(result[3]);
        this.sexeElev = convertToString(result[4]);
        this.celetud = convertToString(result[5]);
        this.desNat = convertToString(result[6]);
        this.codeDetcla = convertToString(result[7]);
    }

    // Méthode utilitaire pour convertir en String de manière sécurisée
    private String convertToString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Character) {
            return String.valueOf((Character) obj);
        }
        // Pour tout autre type, utiliser toString()
        return obj.toString();
    }

    // Méthode utilitaire pour convertir en Date de manière sécurisée
    private Date convertToDate(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof java.sql.Timestamp) {
            return new Date(((java.sql.Timestamp) obj).getTime());
        }
        if (obj instanceof java.sql.Date) {
            return new Date(((java.sql.Date) obj).getTime());
        }
        // Si c'est une String, essayer de parser
        if (obj instanceof String) {
            try {
                // Vous pouvez ajouter un format de date spécifique si nécessaire
                // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                // return format.parse((String) obj);
                return new Date(); // Pour l'instant, retourner la date actuelle
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

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

    @Override
    public String toString() {
        return "EtudiantDTO{" +
                "matriElev='" + matriElev + '\'' +
                ", nomElev='" + nomElev + '\'' +
                ", codeDetcla='" + codeDetcla + '\'' +
                '}';
    }
}