package com.pigierbackend.eleves;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.sql.Connection;
import java.io.ByteArrayOutputStream;


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
    @Override
  public byte[] listeEtudiantExcel(Map<String, Object> parameters) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            
            // Exécuter la requête SQL (adaptez-la selon vos besoins)
            String sql = "SELECT e.Matri_Elev, e.Nom_Elev, e.Lieunais_Elev, e.Datenais_Elev, " +
                   "e.Sexe_Elev, e.celetud, n.Des_Nat, e.Code_Detcla " +
                   "FROM \"Nationalité\" n " +
                   "INNER JOIN \"Elèves\" e ON e.\"Code_Nat\" = n.\"Code_Nat\" " +
                   "WHERE e.\"AnneeSco_Elev\" = '" + parameters.get("PARAMEANNE") + "' " +
                   "AND e.\"Code_Detcla\" LIKE '%" + parameters.get("PARAMCLASSE") + "%' " +
                   "UNION " +
                   "SELECT h.\"Matri_Elev\", h.\"Nom_Elev\", h.\"Lieunais_Elev\", h.\"Datenais_Elev\", " +
                   "h.\"Sexe_Elev\", h.celetud, n.\"Des_Nat\", h.\"Code_Detcla\" " +
                   "FROM \"Nationalité\" n " +
                   "INNER JOIN \"Historique\" h ON h.\"Code_Nat\" = n.\"Code_Nat\" " +
                   "WHERE h.\"AnneeSco_Elev\" = '" + parameters.get("PARAMEANNE") + "' " +
                   "AND h.\"Code_Detcla\" LIKE '%" + parameters.get("PARAMCLASSE") + "%' " +
                   "ORDER BY \"Code_Detcla\", \"Nom_Elev\"";
            
            rs = stmt.executeQuery(sql);
            
            // Créer un nouveau workbook Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Liste des Élèves");
            
            // Créer le style pour l'en-tête
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            
            // Créer la ligne d'en-tête
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Matricule", "Nom", "Lieu de naissance", "Date de naissance", 
                              "Sexe", "Téléphone", "Nationalité", "Classe"};
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Remplir les données
            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(rs.getString("Matri_Elev"));
                row.createCell(1).setCellValue(rs.getString("Nom_Elev"));
                row.createCell(2).setCellValue(rs.getString("Lieunais_Elev"));
                
                // Formater la date
                Cell dateCell = row.createCell(3);
                if (rs.getDate("Datenais_Elev") != null) {
                    dateCell.setCellValue(rs.getDate("Datenais_Elev"));
                    CellStyle dateStyle = workbook.createCellStyle();
                    dateStyle.setDataFormat((short)14); // Format date court
                    dateCell.setCellStyle(dateStyle);
                }
                
                row.createCell(4).setCellValue(rs.getString("Sexe_Elev"));
                row.createCell(5).setCellValue(rs.getString("celetud"));
                row.createCell(6).setCellValue(rs.getString("Des_Nat"));
                row.createCell(7).setCellValue(rs.getString("Code_Detcla"));
            }
            
            // Auto-size les colonnes
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Écrire le workbook dans le ByteArrayOutputStream
            workbook.write(out);
            return out.toByteArray();
            
        } catch (Exception e) {
            System.out.println("Erreur lors de la génération du fichier Excel : " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Erreur lors de la génération Excel: " + e.getMessage());
        } finally {
            // Fermer les ressources
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (out != null) out.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }


}
