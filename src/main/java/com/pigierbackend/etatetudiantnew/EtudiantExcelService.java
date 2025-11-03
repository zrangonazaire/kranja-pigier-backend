package com.pigierbackend.etatetudiantnew;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtudiantExcelService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    public byte[] generateExcelFile(String annee, String etab, String classe) throws IOException {
        System.out.println("Début génération Excel - Paramètres: annee=" + annee + ", etab=" + etab + ", classe=" + classe);
        
        try {
            List<Object[]> results = etudiantRepository.findEtudiantsByCriteriaNative(annee, etab, classe);
            
            System.out.println("Nombre de résultats de la requête: " + results.size());
            
            // Debug: Afficher le premier résultat pour voir la structure
            if (!results.isEmpty()) {
                System.out.println("Premier résultat:");
                Object[] firstResult = results.get(0);
                for (int i = 0; i < firstResult.length; i++) {
                    System.out.println("  Colonne " + i + ": " + firstResult[i] + " (type: " + 
                                     (firstResult[i] != null ? firstResult[i].getClass().getName() : "null") + ")");
                }
            }
            
            // Convertir les résultats en DTOs
            List<EtudiantDTO> etudiants = results.stream()
                    .map(result -> {
                        try {
                            return new EtudiantDTO(result);
                        } catch (Exception e) {
                            System.err.println("Erreur lors de la conversion d'un résultat: " + e.getMessage());
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(dto -> dto != null) // Filtrer les conversions échouées
                    .collect(Collectors.toList());
            
            System.out.println("Nombre d'étudiants après conversion: " + etudiants.size());
            
            if (etudiants.isEmpty()) {
                System.out.println("Aucun étudiant trouvé après conversion");
                return new byte[0];
            }
            
            try (Workbook workbook = new XSSFWorkbook(); 
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                
                Sheet sheet = workbook.createSheet("Liste des Etudiants");
                
                // Créer les en-têtes
                Row headerRow = sheet.createRow(0);
                String[] headers = {
                    "Matricule", "Nom", "Lieu de naissance", "Date de naissance", 
                    "Sexe", "Téléphone", "Nationalité", "Classe"
                };
                
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }
                
                // Remplir les données
                int rowNum = 1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                
                for (EtudiantDTO etudiant : etudiants) {
                    Row row = sheet.createRow(rowNum++);
                    
                    row.createCell(0).setCellValue(etudiant.getMatriElev() != null ? etudiant.getMatriElev() : "");
                    row.createCell(1).setCellValue(etudiant.getNomElev() != null ? etudiant.getNomElev() : "");
                    row.createCell(2).setCellValue(etudiant.getLieunaisElev() != null ? etudiant.getLieunaisElev() : "");
                    
                    if (etudiant.getDatenaisElev() != null) {
                        row.createCell(3).setCellValue(dateFormat.format(etudiant.getDatenaisElev()));
                    } else {
                        row.createCell(3).setCellValue("");
                    }
                    
                    row.createCell(4).setCellValue(etudiant.getSexeElev() != null ? etudiant.getSexeElev() : "");
                    row.createCell(5).setCellValue(etudiant.getCeletud() != null ? etudiant.getCeletud() : "");
                    row.createCell(6).setCellValue(etudiant.getDesNat() != null ? etudiant.getDesNat() : "");
                    row.createCell(7).setCellValue(etudiant.getCodeDetcla() != null ? etudiant.getCodeDetcla() : "");
                }
                
                // Ajuster automatiquement la largeur des colonnes
                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }
                
                workbook.write(outputStream);
                System.out.println("Fichier Excel généré avec succès - Taille: " + outputStream.size() + " bytes");
                return outputStream.toByteArray();
            }
            
        } catch (Exception e) {
            System.err.println("Erreur dans generateExcelFile: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Erreur lors de la génération du fichier Excel", e);
        }
    }
}