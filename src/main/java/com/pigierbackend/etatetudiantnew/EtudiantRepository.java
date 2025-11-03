package com.pigierbackend.etatetudiantnew;

import com.pigierbackend.etatetudiantnew.Etudiant; // ← IMPORTANT: Utilisez l'entité
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, String> { // ← Etudiant, pas EtudiantDTO
    
    @Query(value = """
        SELECT 
            e.\"Matri_Elev\" as matriElev,
            e.\"Nom_Elev\" as nomElev,
            e.\"Lieunais_Elev\" as lieunaisElev,
            e.\"Datenais_Elev\" as datenaisElev,
            e.\"Sexe_Elev\" as sexeElev,
            e.celetud as celetud,
            n.\"Des_Nat\" as desNat,
            e.\"Code_Detcla\" as codeDetcla
        FROM dbo.\"Nationalité\" n
        INNER JOIN dbo.\"Elèves\" e ON 
            e.\"Code_Nat\" = n.\"Code_Nat\" 
            AND e.\"AnneeSco_Elev\" = :annee 
            AND e.\"Etab_source\" = :etab 
            AND e.\"Code_Detcla\" LIKE CONCAT('%', :classe, '%')
        UNION 
        SELECT 
            h.\"Matri_Elev\" as matriElev,
            h.\"Nom_Elev\" as nomElev,
            h.\"Lieunais_Elev\" as lieunaisElev,
            h.\"Datenais_Elev\" as datenaisElev,
            h.\"Sexe_Elev\" as sexeElev,
            h.celetud as celetud,
            n.\"Des_Nat\" as desNat,
            h.\"Code_Detcla\" as codeDetcla
        FROM dbo.\"Nationalité\" n
        INNER JOIN dbo.\"Historique\" h ON 
            h.\"Code_Nat\" = n.\"Code_Nat\" 
            AND h.\"AnneeSco_Elev\" = :annee 
            AND h.\"Etab_source\" = :etab 
            AND h.\"Code_Detcla\" LIKE CONCAT('%', :classe, '%')
        ORDER BY nomElev ASC
        """, nativeQuery = true)
    List<Object[]> findEtudiantsByCriteriaNative(
        @Param("annee") String annee,
        @Param("etab") String etab,
        @Param("classe") String classe
    );
}