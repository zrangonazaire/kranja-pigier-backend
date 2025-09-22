package com.pigierbackend.etatetudiant;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

@Service
public class ExcelService {
    
    @Autowired
    private EtatEleveRepository eleveRepository;
    
    public byte[] generateExcelFile(String anneeSco, String etabSource, String niveau, 
                                  Date startDate, Date endDate) throws Exception {
        
        List<Eleve> eleves = eleveRepository.findElevesByCriteria(anneeSco, etabSource, niveau, startDate, endDate);
        
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Élèves");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Matricule", "Date Inscription", "Nom", "Téléphone", 
                              "Université/Métiers", "Niveau", "Code Classe"};
            
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Create data rows
            int rowNum = 1;
            for (Eleve eleve : eleves) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(eleve.getMatriElev());
                row.createCell(1).setCellValue(eleve.getDateInscriEleve().toString());
                row.createCell(2).setCellValue(eleve.getNomElev());
                row.createCell(3).setCellValue(eleve.getCelEtud());
                row.createCell(4).setCellValue(eleve.getUnivMetiers());
                row.createCell(5).setCellValue(eleve.getNiveau());
                row.createCell(6).setCellValue(eleve.getCodeDetcla());
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(out);
            return out.toByteArray();
        }
    }
}