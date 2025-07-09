package com.pigierbackend.encaissement;

import java.io.File;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
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


@DependsOnDatabaseInitialization
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EncaissementServiceImpl implements EncaissementService {
 final   DataSource dataSource;

    @Override
    public byte[] generateJournalEncaissementsBetweenDatesReport(Map<String, Object> parameters) throws Exception {
 
        try  {
            String path="src/main/resources/etat/template";
File file=ResourceUtils.getFile(path+"/point_encaissement_tout_par_caisse_deux_periode.jrxml");
             System.out.println("******reportPath: " + file);
            // Charger le rapport
               JasperReport jasperreport=JasperCompileManager.compileReport(file.getAbsolutePath());
            File di=new File(path+"/depot_etat");
            boolean dir=di.mkdir();
            if (dir) {
                System.out.println("Dossier cree");
                
            }
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperreport, parameters, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"/depot_etat/poindecaisseentredeuxdate.pdf");
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

    @Override
    public byte[] generateJournalDroitInscrisBetweenDatesReport(Map<String, Object> parameters) throws Exception {
         try  {
            String path="src/main/resources/etat/template";
File file=ResourceUtils.getFile(path+"/point_encaissement_doit_inscrit_par_caisse_deux_periode.jrxml");
             System.out.println("******reportPath: " + file);
            // Charger le rapport
               JasperReport jasperreport=JasperCompileManager.compileReport(file.getAbsolutePath());
            File di=new File(path+"/depot_etat");
            boolean dir=di.mkdir();
            if (dir) {
                System.out.println("Dossier cree");
                
            }
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperreport, parameters, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, path+"/depot_etat/poindecaissedroitinscentredeuxdate.pdf");
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
