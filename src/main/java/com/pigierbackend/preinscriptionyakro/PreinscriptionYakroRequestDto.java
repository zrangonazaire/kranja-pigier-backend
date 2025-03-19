package com.pigierbackend.preinscriptionyakro;

import java.time.LocalDate;

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
public class PreinscriptionYakroRequestDto {

    String id; // Changed from String to Long
    @NotBlank(message = "Saisie obligatoire.")
    @NotEmpty(message = "Saisie obligatoire.")
    @NotNull(message = "Saisie obligatoire.")
    String nomprenoms; // varchar(150) Checked
    LocalDate datnais; // datetime Checked

    String lieunais; // varchar(100) Checked

    String sexe; // varchar(1) Checked

    String nationalite; // varchar(50) Checked

    String natident; // varchar(30) Checked

    String numidentite; // varchar(50) Checked
    @NotBlank(message = "Saisie obligatoire.")
    @NotEmpty(message = "Saisie obligatoire.")
    @NotNull(message = "Saisie obligatoire.")
    String teletud; // varchar(30) Checked

    String celetud; // varchar(30) Checked

    String emailetud; // varchar(100) Checked

    String viletud; // varchar(150) Checked

    String cometud; // varchar(150) Checked

    String baccalaureat; // varchar(3) Checked

    String annbac; // varchar(5) Checked

    String diplequiv; // varchar(150) Checked

    String anndiplequiv; // varchar(5) Checked

    String nivoetud; // varchar(100) Checked

    String annivoetud; // varchar(100) Checked

    String grade; // varchar(100) Checked

    String anngrad; // varchar(100) Checked

    String specgrad; // varchar(100) Checked

    String etsfreq; // varchar(150) Checked

    String formsouh; // varchar(200) Checked

    String idperm; // varchar(20) Checked

    String nompere; // varchar(150) Checked

    String nomere; // varchar(150) Checked

    String titrespo; // varchar(10) Checked

    String respo; // varchar(10) Checked

    String nomrespo; // varchar(150) Checked

    String profrespo; // varchar(100) Checked

    String emprespo; // varchar(150) Checked

    String vilrespo; // varchar(150) Checked

    String comrespo; // varchar(150) Checked

    String bprespo; // varchar(50) Checked

    String celrespo; // varchar(30) Checked

    String telburespo; // varchar(30) Checked

    String teldomrespo; // varchar(30) Checked

    String emailrespo; // varchar(100) Checked

    String copiebac; // varchar(150) Checked

    String copderndipl; // varchar(150) Checked

    String contnompren1; // varchar(150) Checked

    String contadr1; // varchar(100) Checked

    String contel1; // varchar(30) Checked

    String contcel1; // varchar(30) Checked

    String contnompren2; // varchar(150) Checked

    String contadr2; // varchar(100) Checked

    String contel2; // varchar(30) Checked

    String contcel2; // varchar(30) Checked

    String clindec; // varchar(5) Checked

    String clinnom; // varchar(100) Checked

    String clintel; // varchar(30) Checked

    String clinmed; // varchar(100) Checked

    String clinmedcont; // varchar(30) Checked

    String maladies; // varchar(300) Checked

    String soins; // varchar(300) Checked

    String medic; // varchar(300) Checked

    String premsoins; // varchar(300) Checked

    String intervchir; // varchar(300) Checked
    LocalDate datinscrip; // smalldatetime Checked

    String decision; // varchar(3) Checked

    String numtabl; // varchar(20) Checked

    String numatri; // varchar(20) Checked
    int totbac; // int Checked

    String matpc; // varchar(20) Checked

    String anneescolaire; // varchar(10) Checked

    String Etab_source; // varchar(25) Checked
}
