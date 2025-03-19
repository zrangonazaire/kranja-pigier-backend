package com.pigierbackend.preinscriptionyakro;

import java.time.LocalDate;

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

@Table(name = "preinscriptionyakro")
@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PREINSCRIPTIONYAKRO {
    @Id
    @Column(length = 12, nullable = false)
    String id; // Changed from String to Long
    @Column(nullable = false, length = 150)
    String nomprenoms; // varchar(150) Checked
    LocalDate datnais; // datetime Checked
    @Column(length = 100)
    String lieunais; // varchar(100) Checked
    @Column(length = 1)
    String sexe; // varchar(1) Checked
    @Column(length = 50)
    String nationalite; // varchar(50) Checked
    @Column(length = 30)
    String natident; // varchar(30) Checked
    @Column(length = 50)
    String numidentite; // varchar(50) Checked
    @Column(length = 30)
    String teletud; // varchar(30) Checked
    @Column(length = 30)
    String celetud; // varchar(30) Checked
    @Column(length = 100)
    String emailetud; // varchar(100) Checked
    @Column(length = 150)
    String viletud; // varchar(150) Checked
    @Column(length = 150)
    String cometud; // varchar(150) Checked
    @Column(length = 3)
    String baccalaureat; // varchar(3) Checked
    @Column(length = 5)
    String annbac; // varchar(5) Checked
    @Column(length = 150)
    String diplequiv; // varchar(150) Checked
    @Column(length = 5)
    String anndiplequiv; // varchar(5) Checked
    @Column(length = 100)
    String nivoetud; // varchar(100) Checked
    @Column(length = 100)
    String annivoetud; // varchar(100) Checked
    @Column(length = 100)
    String grade; // varchar(100) Checked
    @Column(length = 100)
    String anngrad; // varchar(100) Checked
    @Column(length = 100)
    String specgrad; // varchar(100) Checked
    @Column(length = 150)
    String etsfreq; // varchar(150) Checked
    @Column(length = 200)
    String formsouh; // varchar(200) Checked
    @Column(length = 20)
    String idperm; // varchar(20) Checked
    @Column(length = 150)
    String nompere; // varchar(150) Checked
    @Column(length = 150)
    String nomere; // varchar(150) Checked
    @Column(length = 10)
    String titrespo; // varchar(10) Checked
    @Column(length = 10)
    String respo; // varchar(10) Checked
    @Column(length = 150)
    String nomrespo; // varchar(150) Checked
    @Column(length = 100)
    String profrespo; // varchar(100) Checked
    @Column(length = 150)
    String emprespo; // varchar(150) Checked
    @Column(length = 150)
    String vilrespo; // varchar(150) Checked
    @Column(length = 150)
    String comrespo; // varchar(150) Checked
    @Column(length = 50)
    String bprespo; // varchar(50) Checked
    @Column(length = 30)
    String celrespo; // varchar(30) Checked
    @Column(length = 30)
    String telburespo; // varchar(30) Checked
    @Column(length = 30)
    String teldomrespo; // varchar(30) Checked
    @Column(length = 100)
    String emailrespo; // varchar(100) Checked
    @Column(length = 150)
    String copiebac; // varchar(150) Checked
    @Column(length = 150)
    String copderndipl; // varchar(150) Checked
    @Column(length = 150)
    String contnompren1; // varchar(150) Checked
    @Column(length = 100)
    String contadr1; // varchar(100) Checked
    @Column(length = 30)
    String contel1; // varchar(30) Checked
    @Column(length = 30)
    String contcel1; // varchar(30) Checked
    @Column(length = 150)
    String contnompren2; // varchar(150) Checked
    @Column(length = 100)
    String contadr2; // varchar(100) Checked
    @Column(length = 30)
    String contel2; // varchar(30) Checked
    @Column(length = 30)
    String contcel2; // varchar(30) Checked
    @Column(length = 5)
    String clindec; // varchar(5) Checked
    @Column(length = 100)
    String clinnom; // varchar(100) Checked
    @Column(length = 30)
    String clintel; // varchar(30) Checked
    @Column(length = 100)
    String clinmed; // varchar(100) Checked
    @Column(length = 30)
    String clinmedcont; // varchar(30) Checked
    @Column(length = 300)
    String maladies; // varchar(300) Checked
    @Column(length = 300)
    String soins; // varchar(300) Checked
    @Column(length = 300)
    String medic; // varchar(300) Checked
    @Column(length = 300)
    String premsoins; // varchar(300) Checked
    @Column(length = 300)
    String intervchir; // varchar(300) Checked
    LocalDate datinscrip; // smalldatetime Checked
    @Column(length = 3)
    String decision; // varchar(3) Checked
    @Column(length = 20)
    String numtabl; // varchar(20) Checked
    @Column(length = 20)
    String numatri; // varchar(20) Checked
    int totbac; // int Checked
    @Column(length = 20)
    String matpc; // varchar(20) Checked
    @Column(length = 10)
    String anneescolaire; // varchar(10) Checked
    @Column(length = 25)
    String Etab_source; // varchar(25) Checked
    @Column(length = 25,nullable = true)
    boolean Inscrit_Sous_Titre; // bit Checked
}
