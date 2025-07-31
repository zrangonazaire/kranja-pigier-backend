package com.pigierbackend.preinscription;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreinscriptionRepository extends JpaRepository<PREINSCRIPTION,String> {
    List<PREINSCRIPTION> findByDateInscriptionBetween(LocalDateTime start, LocalDateTime end, Sort sort);
}
