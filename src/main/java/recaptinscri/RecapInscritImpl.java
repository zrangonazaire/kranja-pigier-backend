package recaptinscri;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecapInscritImpl extends JpaRepository<RecapInscritDo, Long> {
    
    @Query(value = "SELECT " +
            "dc.Code_Cla AS codeClasse, " +
            "CONCAT(" +
            "  CASE MONTH(e.Date_Encais) " +
            "    WHEN 1 THEN 'JANVIER' " +
            "    WHEN 2 THEN 'FEVRIER' " +
            "    WHEN 3 THEN 'MARS' " +
            "    WHEN 4 THEN 'AVRIL' " +
            "    WHEN 5 THEN 'MAI' " +
            "    WHEN 6 THEN 'JUIN' " +
            "    WHEN 7 THEN 'JUILLET' " +
            "    WHEN 8 THEN 'AOUT' " +
            "    WHEN 9 THEN 'SEPTEMBRE' " +
            "    WHEN 10 THEN 'OCTOBRE' " +
            "    WHEN 11 THEN 'NOVEMBRE' " +
            "    WHEN 12 THEN 'DECEMBRE' " +
            "  END, " +
            "  ' ', " +
            "  YEAR(e.Date_Encais)" +
            ") AS moisAnnee, " +
            "COUNT(el.Matri_Elev) AS nombreInscrits, " +
            "?1 AS anneeAcademique " +
            "FROM `Elèves` el " +
            "INNER JOIN `Détail Classes` dc ON dc.Code_Detcla = el.Code_Detcla " +
            "INNER JOIN `Encaissements des Elèves Pl` e ON el.Matri_Elev = e.Matri_Elev " +
            "WHERE el.AnneeSco_Elev = ?1 " +
            "AND (e.Objet_Encais = 'I' OR e.Objet_Encais = 'R') " +
            "AND e.anneeScolEncaissElevPL = el.AnneeSco_Elev " +
            "AND (dc.Code_Cla LIKE '%LP%2%' OR dc.Code_Cla LIKE '%BTS%2') " +
            "GROUP BY dc.Code_Cla, YEAR(e.Date_Encais), MONTH(e.Date_Encais)", 
            nativeQuery = true)
    List<RecapInscritProjection> getRecapInscrits(String anneeAcademique);
    
    public interface RecapInscritProjection {
        String getCodeClasse();
        String getMoisAnnee();
        Integer getNombreInscrits();
        String getAnneeAcademique();
    }
}