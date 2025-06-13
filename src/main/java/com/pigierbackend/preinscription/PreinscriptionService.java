package com.pigierbackend.preinscription;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import net.sf.jasperreports.engine.JRException;

public interface PreinscriptionService {
    List<PreinscriptionResponseDto> getAllPreinscription(int page, int size);
    List<PreinscriptionResponseDto> getAllPreinscription( int size);
    List<PreinscriptionResponseDto> getAllPreinscription();

    PreinscriptionResponseDto getPreinscriptionById(String id);

    PreinscriptionResponseDto createOrUpdatePreinscription(
            PreinscriptionRequestDto preinscriptionYakroRequestDto);

    Boolean deletePreinscription(String id);

    List<PreinscriptionResponseDto> getPreinscriptionByNomEleve(String nomEleve);
    byte[] impressionPreinscription(String id) throws FileNotFoundException, JRException, SQLException;
    byte[] impressionInscription(String id) throws FileNotFoundException, JRException, SQLException;
    byte[] impressionFicheMedicale(String id) throws FileNotFoundException, JRException, SQLException;

}
