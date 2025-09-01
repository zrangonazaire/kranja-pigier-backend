package com.pigierbackend.eleves;

import java.util.List;
import java.util.Map;

public interface EleveService {

 byte[] listeEtudiant(Map<String, Object> parameters) throws Exception;

 byte[] listeEtudiantExcel(Map<String, Object> parameters) throws Exception;
List<EleveRecordDTO> getPromotionsEleves(List<String> promotions, List<String> etablissements, String anneeScolaire)throws Exception ;
byte[]  getPromotionsElevesExcel(List<String> promotions, List<String> etablissements, String anneeScolaire)throws Exception ;
List<String> getAllClasses(String anneeScolaire)throws Exception ;
}
