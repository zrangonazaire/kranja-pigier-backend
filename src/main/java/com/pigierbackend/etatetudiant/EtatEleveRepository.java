package com.pigierbackend.etatetudiant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface EtatEleveRepository extends JpaRepository<Eleve, String> {
    
    @Query("SELECT e FROM Eleve e WHERE e.anneeScoElev = :anneeSco " +
           "AND e.etabSource = :etabSource " +
           "AND e.niveau = :niveau " +
           "AND CAST(e.dateInscriEleve AS date) BETWEEN :startDate AND :endDate")
    List<Eleve> findElevesByCriteria(
            @Param("anneeSco") String anneeSco,
            @Param("etabSource") String etabSource,
            @Param("niveau") String niveau,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);
}