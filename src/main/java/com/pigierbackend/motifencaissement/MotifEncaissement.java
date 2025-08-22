package com.pigierbackend.motifencaissement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "Motif_Encaissement")
@Data
@Builder
public class MotifEncaissement {
    @Id
    @Column(name = "CodeMotifEnc", length = 1, nullable = false)
    private String codeMotifEnc;

    @Column(name = "LibMotifEnc", length = 50)
    private String libMotifEnc;
}
