package com.pigierbackend.privilege;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByNomPrivilege(String nomPrivilege);

}
