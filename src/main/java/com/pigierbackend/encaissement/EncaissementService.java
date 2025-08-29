package com.pigierbackend.encaissement;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EncaissementService {
  // Journal de caissements entre deux dates
  byte[] generateJournalEncaissementsBetweenDatesReport(Map<String, Object> parameters) throws Exception;

  byte[] generateJournalDroitInscrisBetweenDatesReport(Map<String, Object> parameters) throws Exception;

   byte[] generateEtatFacturationReport(Map<String, Object> parameters) throws Exception;

  public List<EncaissementDTO> getEncaissementsEntreDeuxPeriodeEtabSource(Map<String, Object> parameters)
      throws SQLException;
}
