package com.pigierbackend.eleves;

import java.io.File;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EleveServiceImpl implements EleveService {
 final   DataSource dataSource;
    @Override
    public byte[] listeEtudiant(Map<String, Object> parameters) throws Exception {
             
        
        try  {

            String path="src/main/resources/etat/template";
File file=ResourceUtils.getFile(path+"/fichedeclasse.jrxml");
             System.out.println("******reportPath: " + file);
            // Charger le rapport
               JasperReport jasperreport=JasperCompileManager.compileReport(file.getAbsolutePath());
               
            File di=new File(path+"/depot_etat");
            boolean dir=di.mkdir();
            if (dir) {
                System.out.println("Dossier cree");
                
            }
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperreport, parameters, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"/depot_etat/fichedeclasse.pdf");
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du rapport : " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Erreur: " + e.getMessage());
        } finally {
            // Fermer les ressources si nécessaire
            if (dataSource != null) {
                try {
                    dataSource.getConnection().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

       
    }


}
