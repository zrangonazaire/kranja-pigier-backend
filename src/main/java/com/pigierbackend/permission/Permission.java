package com.pigierbackend.permission;

import org.springframework.security.core.GrantedAuthority;

import com.pigierbackend.abstractentity.AbstractEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Permission extends AbstractEntity implements GrantedAuthority {
    String nomPermission;
    String descriptionPermission;
    String module; // Employé, Client, Commande, etc.
    boolean canRead;
    boolean canWrite;
    boolean canEdit;
    boolean canDelete;

    @Override
    public String getAuthority() {
        return nomPermission;
    }

    public PermissionResponse toPermissionResponse() {
        return PermissionResponse.builder()
                .id(getId())
                .nomPermission(getNomPermission())
                .descriptionPermission(getDescriptionPermission())
                .createdDate(this.getCreationDate())
                .lastModifiedDate(this.getModificationDate())
                .build();
    }

}
