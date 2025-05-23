package com.pigierbackend.encaissement;

import java.util.Map;

public interface EncaissementService {
//Journal de caissements entre deux dates
    byte[] generateJournalEncaissementsBetweenDatesReport(Map<String, Object> parameters) throws Exception;
      byte[] generateJournalDroitInscrisBetweenDatesReport(Map<String, Object> parameters) throws Exception;

}
