package com.pigierbackend.encaissement;

import com.pigierbackend.eleves.ELEVE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EncaissementBIRepository extends JpaRepository<ELEVE, String> {

    @Query(value = """
SELECT 
    el.Matri_Elev AS matricule,
    CONCAT(el.Nom_Elev, ' ', el.Prenom_Elev) AS nomPrenom,
    el.Cycle_Elev AS niveau,
    el.Nom_Cla AS filiere,

    (COALESCE(el.MontantSco_Elev,0)
     + COALESCE(el.droitinscription,0)
     + COALESCE(el.Cotisation,0)
     + COALESCE(el.montantExamen,0)) AS montantAttendu,

    COALESCE(SUM(
        CASE 
            WHEN (:mois IS NULL OR MONTH(e.Date_Encais) = :mois)
            THEN e.Montant_Encais
            ELSE 0
        END
    ),0) AS montantEncaisse,

    (COALESCE(el.SoldSco_Elev,0) + COALESCE(el.SoldFraisExam,0)) AS solde

FROM [Elèves] el
LEFT JOIN [Encaissements des Elèves Pl] e
       ON e.Matri_Elev = el.Matri_Elev
      AND (:annee IS NULL OR e.anneeScolEncaissElevPL = :annee)

WHERE (:annee IS NULL OR el.AnneeSco_Elev = :annee)
  AND (:niveau IS NULL OR el.Cycle_Elev = :niveau)

GROUP BY 
    el.Matri_Elev,
    el.Nom_Elev,
    el.Prenom_Elev,
    el.Cycle_Elev,
    el.Nom_Cla,
    el.MontantSco_Elev,
    el.droitinscription,
    el.Cotisation,
    el.montantExamen,
    el.SoldSco_Elev,
    el.SoldFraisExam
""", nativeQuery = true)
    List<Object[]> getBaseBI(
            @Param("annee") String annee,
            @Param("mois") Integer mois,
            @Param("niveau") String niveau
    );

}

