package com.pigierbackend.privilege;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByNomPrivilege(String nomPrivilege);

}
