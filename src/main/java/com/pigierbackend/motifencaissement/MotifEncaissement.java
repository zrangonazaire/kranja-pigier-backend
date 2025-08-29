package com.pigierbackend.motifencaissement;

import java.util.List;

import com.pigierbackend.encaissement.EncaissementElevePL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Motif_Encaissement")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MotifEncaissement {
    @Id
    @Column(name = "CodeMotifEnc", nullable = false, length = 1,columnDefinition = "NCHAR(1)")
    private String codeMotifEnc;

    // Using 'length' is more portable. The 'N' for Unicode is often handled by the dialect.
    @Column(name = "LibMotifEnc", length = 50)
    private String libMotifEnc;
}
