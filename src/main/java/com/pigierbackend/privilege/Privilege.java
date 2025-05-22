package com.pigierbackend.privilege;

import com.pigierbackend.abstractentity.AbstractEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "PRIVILEGE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Ajout du constructeur par défaut requis par JPA
public class Privilege extends AbstractEntity {
    String nomPrivilege;
    String descriptionPrivilege;
    
}
