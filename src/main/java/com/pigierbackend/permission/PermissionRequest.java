package com.pigierbackend.permission;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRequest {
    String nomPermission;
    String descriptionPermission;
    String module; // Employé, Client, Commande, etc.
    boolean canRead;
    boolean canWrite;
    boolean canEdit;
    boolean canDelete;

    public Permission toPermission() {
        return new Permission(this.nomPermission, this.descriptionPermission, this.module, this.canRead, this.canWrite,
                this.canEdit, this.canDelete);
    }
}