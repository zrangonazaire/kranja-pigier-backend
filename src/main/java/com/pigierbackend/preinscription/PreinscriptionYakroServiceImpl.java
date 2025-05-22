package com.pigierbackend.preinscription;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.data.domain.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@RequiredArgsConstructor
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreinscriptionYakroServiceImpl implements PreinscriptionYakroService {

    final PreinscriptionYakroRepository preinscriptionYakroRepository;
    final PreinscriptionYakroMapper preinscriptionYakroMapper;
    final DataSource dataSource;
    @Override
    public List<PreinscriptionYakroResponseDto> getAllPreinscriptionYakro(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "datinscrip"));
        return preinscriptionYakroRepository.findAll(pageable)
                .stream()
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .distinct()                
                .collect(Collectors.toList());
    }

    @Override
    public PreinscriptionYakroResponseDto getPreinscriptionYakroById(String id) {
        return preinscriptionYakroRepository.findById(id)
                .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
                .orElseThrow(() -> new IllegalArgumentException("La péinscription n'a pas été trouvée"));
    }

    @Override
    public PreinscriptionYakroResponseDto createOrUpdatePreinscriptionYakro(
            PreinscriptionYakroRequestDto preinscriptionYakroRequestDto) {
        PREINSCRIPTION preinscriptionyakro = preinscriptionYakroRepository
                .findById(preinscriptionYakroRequestDto.getId()).orElse(new PREINSCRIPTION());
        BeanUtils.copyProperties(preinscriptionYakroRequestDto, preinscriptionyakro);
        preinscriptionYakroRepository.save(preinscriptionyakro);
        return preinscriptionYakroMapper.fromPreinscriptionYakro(preinscriptionyakro);
    }

    @Override
    public Boolean deletePreinscriptionYakro(String id) {
          Optional<PREINSCRIPTION> preinscYak = preinscriptionYakroRepository.findById(id);
        if (preinscYak.isPresent()) {
            preinscriptionYakroRepository.delete(preinscYak.get());
            return true;
        }
        return false;
    }

    @Override
    public List<PreinscriptionYakroResponseDto> getPreinscriptionYakroByNomEleve(String nomEleve) {
        return preinscriptionYakroRepository.findAll()
        .stream()
        .filter(x->x.getNomprenoms().contains(nomEleve))
        .map(preinscriptionYakroMapper::fromPreinscriptionYakro)
        .collect(Collectors.toList());
    }

    @Override
    public byte[] impressionPreinscriptionYakro(String id)throws FileNotFoundException, JRException, SQLException {
      
            String path="src/main/resources/etat/template";
            
            File file=ResourceUtils.getFile(path+"/fichierpreincription.jrxml");
            Map<String,Object> parameters=new HashMap<>();
            parameters.put("id_param", id);
            JasperReport jasperreport=JasperCompileManager.compileReport(file.getAbsolutePath());
            File di=new File(path+"/depot_etat");
            boolean dir=di.mkdir();
            if (dir) {
                System.out.println("Dossier cree");
                
            }
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperreport, parameters, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"/depot_etat/fichierpreincription"+id+".pdf");
            return JasperExportManager.exportReportToPdf(jasperPrint);

      
    }

    @Override
    public byte[] impressionInscriptionYakro(String id) throws FileNotFoundException, JRException, SQLException{
        String path="src/main/resources/etat/template";
            
        File file=ResourceUtils.getFile(path+"/incriptioneport.jrxml");
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("id_param", id);
        JasperReport jasperreport=JasperCompileManager.compileReport(file.getAbsolutePath());
        File di=new File(path+"/depot_etat");
        boolean dir=di.mkdir();
        if (dir) {
            System.out.println("Dossier cree");
            
        }
        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperreport, parameters, dataSource.getConnection());
        JasperExportManager.exportReportToPdfFile(jasperPrint, path+"/depot_etat/incriptioneport"+id+".pdf");
        return JasperExportManager.exportReportToPdf(jasperPrint);
   
    }

    @Override
    public byte[] impressionFicheMedicaleyakro(String id)throws FileNotFoundException, JRException, SQLException {
        String path="src/main/resources/etat/template";
            
        File file=ResourceUtils.getFile(path+"/medicalreport.jrxml");
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("id_param", id);
        JasperReport jasperreport=JasperCompileManager.compileReport(file.getAbsolutePath());
        File di=new File(path+"/depot_etat");
        boolean dir=di.mkdir();
        if (dir) {
            System.out.println("Dossier cree");
            
        }
        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperreport, parameters, dataSource.getConnection());
        JasperExportManager.exportReportToPdfFile(jasperPrint, path+"/depot_etat/medicalreport"+id+".pdf");
        return JasperExportManager.exportReportToPdf(jasperPrint);
       
    }

}
