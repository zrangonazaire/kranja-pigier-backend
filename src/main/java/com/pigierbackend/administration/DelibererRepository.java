package com.pigierbackend.administration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DelibererRepository extends JpaRepository<Deliberer, Long> {

    Optional<Deliberer> findByIdGroupeAndMatriElevAndCodeUE(
            String idGroupe,
            String matriElev,
            String codeUE
    );
}
