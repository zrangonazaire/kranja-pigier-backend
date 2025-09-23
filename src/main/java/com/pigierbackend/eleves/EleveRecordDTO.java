package com.pigierbackend.eleves;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EleveRecordDTO {
    private String nom;
    private String prenoms;
    private LocalDateTime dateNaissance;
    private String sexe;
    private String emailPersonnel;
    private String codeDetcla;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateIns;

}
