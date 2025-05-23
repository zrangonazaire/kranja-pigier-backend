package com.pigierbackend.eleves;

import java.util.Map;

public interface EleveService {

 byte[] listeEtudiant(Map<String, Object> parameters) throws Exception;

 byte[] listeEtudiantExcel(Map<String, Object> parameters) throws Exception;


}
