package com.pigierbackend.preinscription;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import net.sf.jasperreports.engine.JRException;

public interface PreinscriptionYakroService {
    List<PreinscriptionYakroResponseDto> getAllPreinscriptionYakro(int page, int size);

    PreinscriptionYakroResponseDto getPreinscriptionYakroById(String id);

    PreinscriptionYakroResponseDto createOrUpdatePreinscriptionYakro(
            PreinscriptionYakroRequestDto preinscriptionYakroRequestDto);

    Boolean deletePreinscriptionYakro(String id);

    List<PreinscriptionYakroResponseDto> getPreinscriptionYakroByNomEleve(String nomEleve);
    byte[] impressionPreinscriptionYakro(String id) throws FileNotFoundException, JRException, SQLException;
    byte[] impressionInscriptionYakro(String id) throws FileNotFoundException, JRException, SQLException;
    byte[] impressionFicheMedicaleyakro(String id) throws FileNotFoundException, JRException, SQLException;

}
