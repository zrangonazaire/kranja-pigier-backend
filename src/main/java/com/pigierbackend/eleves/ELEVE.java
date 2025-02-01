package com.pigierbackend.eleves;

import com.pigierbackend.abstractentity.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "ELEVE")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ELEVE extends AbstractEntity {
    @Column(name = "matricule", nullable = false, length = 10, unique = true)
    String matricule;
    String nomprenom;
    @Column(name = "sexe", nullable = false, length = 1)
    String sexe;
    
    @Column(name = "role") // User role
    String role; 
}
