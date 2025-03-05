package com.pigierbackend.privilege;

import com.pigierbackend.abstractentity.AbstractEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "PRIVILEGE")
@Getter
@Setter
public class Privilege extends AbstractEntity {
    String nomPrivilege;
    String descriptionPrivilege;
    
    @Version
    private Long version;
}
