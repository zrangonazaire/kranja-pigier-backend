package com.pigierbackend.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNomRole(String nomRole);

    Optional<Role> findRoleByNomRole(String string);
}
