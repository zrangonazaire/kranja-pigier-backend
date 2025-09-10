package com.pigierbackend.recaptinscri;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;


import com.pigierbackend.recaptinscri.RecapInscritImpl.RecapInscritProjection;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Set;
@Service
public class RecapInscriService {
  @Autowired
    private RecapInscritImpl RecapInscritImpl;
    
    public byte[] generateExcelFile(String anneeAcademique) throws IOException {
        List<RecapInscritProjection> data = RecapInscritImpl.getRecapInscrits(anneeAcademique);
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Recap Inscrits");
            
            // 1. Collecter toutes les classes et tous les mois distincts
            Set<String> classes = data.stream()
                .map(RecapInscritProjection::getCodeClasse)
                .collect(Collectors.toCollection(TreeSet::new));
            
            Set<String> moisAnnees = data.stream()
                .map(RecapInscritProjection::getMoisAnnee)
                .collect(Collectors.toCollection(TreeSet::new));
            
            // 2. Créer une map pour accéder rapidement aux counts
            Map<String, Map<String, Integer>> countMap = new HashMap<>();
            for (RecapInscritProjection item : data) {
                countMap
                    .computeIfAbsent(item.getMoisAnnee(), k -> new HashMap<>())
                    .put(item.getCodeClasse(), item.getNombreInscrits());
            }
            
            // 3. Créer l'en-tête avec les classes
            Row headerRow = sheet.createRow(7);
            headerRow.createCell(0).setCellValue(""); // Cellule vide en haut à gauche
            
            int colNum = 1;
            for (String classe : classes) {
                headerRow.createCell(colNum++).setCellValue(classe);
            }
            headerRow.createCell(colNum).setCellValue("TOTAL");
            
            // 4. Remplir les données par mois
            int rowNum = 8;
            for (String moisAnnee : moisAnnees) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(moisAnnee); // Nom du mois dans la première colonne
                
                int totalMois = 0;
                colNum = 1;
                for (String classe : classes) {
                    Integer count = countMap.getOrDefault(moisAnnee, Collections.emptyMap())
                                          .getOrDefault(classe, 0);
                    row.createCell(colNum++).setCellValue(count);
                    totalMois += count;
                }
                row.createCell(colNum).setCellValue(totalMois); // Total pour le mois
            }
            
            // 5. Ajouter la ligne des totaux par classe
            Row totalRow = sheet.createRow(rowNum);
            totalRow.createCell(0).setCellValue("TOTAL");
            
            colNum = 1;
            int grandTotal = 0;
            for (String classe : classes) {
                int totalClasse = 0;
                for (String moisAnnee : moisAnnees) {
                    totalClasse += countMap.getOrDefault(moisAnnee, Collections.emptyMap())
                                         .getOrDefault(classe, 0);
                }
                totalRow.createCell(colNum++).setCellValue(totalClasse);
                grandTotal += totalClasse;
            }
            totalRow.createCell(colNum).setCellValue(grandTotal); // Grand total
            
            // 6. Ajouter l'année académique
            Row anneeRow = sheet.createRow(5);
            anneeRow.createCell(1).setCellValue("ANNEE ACADEMIQUE:");
            anneeRow.createCell(2).setCellValue(anneeAcademique);
            
            // 7. Auto-size columns
            for (int i = 0; i <= classes.size() + 1; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
}
