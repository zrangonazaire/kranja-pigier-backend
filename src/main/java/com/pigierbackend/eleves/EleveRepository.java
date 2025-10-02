package com.pigierbackend.eleves;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EleveRepository extends JpaRepository<ELEVE, String> {
    // @Query("""
    // SELECT e
    // FROM ELEVE e
    // WHERE e.codeDetcla <> ''
    // AND e.codeDetcla NOT LIKE 'aban%'
    // AND e.codeDetcla IN :promotions
    // AND e.etabSource IN :etablissements
    // AND e.anneeScoElev = :anneeScolaire
    // ORDER BY e.codeDetcla, e.nomElev
    // """)
    // Stream<ELEVE> findValidElevesAsStream(@Param("promotions") List<String>
    // promotions, @Param("etablissements") List<String> etablissements,
    // @Param("anneeScolaire") String anneeScolaire);
    @Query("""
                SELECT e
                FROM ELEVE e
                WHERE e.codeDetcla <> ''
                  AND e.codeDetcla NOT LIKE 'aban%'
                  AND e.codeDetcla IN :promotions
                  AND e.etabSource IN :etablissements
                  AND e.anneeScoElev = :anneeScolaire
                  AND CAST(e.dateInscriEleve AS date) BETWEEN :startDate AND :endDate
                ORDER BY e.codeDetcla, e.nomElev
            """)
    Stream<ELEVE> findValidElevesAsStream(
            @Param("promotions") List<String> promotions,
            @Param("etablissements") List<String> etablissements,
            @Param("anneeScolaire") String anneeScolaire,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("""
                SELECT DISTINCT e.codeDetcla
                FROM ELEVE e
                WHERE e.codeDetcla <> ''
                  AND e.codeDetcla NOT LIKE 'aban%'

                  AND e.anneeScoElev = :anneeScolaire
                ORDER BY e.codeDetcla
            """)
    Stream<String> findValidCodeDetclaAsStream(

            @Param("anneeScolaire") String anneeScolaire);

             @Query("""
    SELECT e
    FROM ELEVE e
    WHERE e.codeDetcla <> ''
      AND e.codeDetcla NOT LIKE 'aban%'
      AND e.codeDetcla IN :promotions
      AND e.etabSource IN :etablissements
      AND e.anneeScoElev = :anneeScolaire
      and(e.montantScoElev+e.droitinscription-e.soldScoElev)>= :montantDu
      AND CAST(e.dateInscriEleve AS date) BETWEEN :startDate AND :endDate
    ORDER BY e.codeDetcla, e.nomElev
""")
Stream<ELEVE> findValidElevesAvecMontantAsStream(
    @Param("promotions") List<String> promotions,
    @Param("etablissements") List<String> etablissements,
    @Param("anneeScolaire") String anneeScolaire,
    @Param("startDate") LocalDate startDate,
    @Param("endDate") LocalDate endDate,
    @Param("montantDu") int montantDu
);
}
