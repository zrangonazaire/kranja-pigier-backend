package com.pigierbackend.administration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrationRepository extends JpaRepository<AdministrationTempo, Long> {

    List<AdministrationTempo> findByTraitement(AdministrationEnum traitement);
}

