package com.pigierbackend.permission;

import org.springframework.security.core.GrantedAuthority;

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
@Table(name = "PERMISSION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Ajout du constructeur par défaut requis par JPA
public class Permission extends AbstractEntity implements GrantedAuthority {
    String nomPrivilege;
    String descriptionPrivilege;

    @Override
    public String getAuthority() {
        return nomPrivilege;
    }

}
