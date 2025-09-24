package com.pigierbackend.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<URole, Long> {
       Optional<URole> findByNomRole(String string);

}
