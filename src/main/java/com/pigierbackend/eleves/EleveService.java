package com.pigierbackend.eleves;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EleveService {

    byte[] listeEtudiant(Map<String, Object> parameters) throws Exception;

    byte[] listeEtudiantExcel(Map<String, Object> parameters) throws Exception;

    List<EleveRecordDTO> getPromotionsEleves(List<String> promotions, List<String> etablissements, String anneeScolaire,
            LocalDate dateDebut, LocalDate dateFin) throws Exception;

    byte[] getPromotionsElevesExcel(List<String> promotions, List<String> etablissements, String anneeScolaire,
            LocalDate dateDebut, LocalDate dateFin) throws Exception;

    byte[] getPromotionsElevesExcels(List<String> promotions, List<String> etablissements, String anneeScolaire,
                                    LocalDate dateDebut, LocalDate dateFin) throws Exception;

    List<String> getAllClasses(String anneeScolaire) throws Exception;
    List<EleveRecordAvecPayerDto> getPromotionsElevesAvecMontantPayer(List<String> promotions, List<String> etablissements, String anneeScolaire, LocalDate dateDebut, LocalDate dateFin,int montantDu) throws Exception;
}
