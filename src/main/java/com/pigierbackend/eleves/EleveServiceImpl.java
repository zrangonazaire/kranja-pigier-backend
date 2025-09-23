package com.pigierbackend.eleves;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.apache.poi.ss.usermodel.*;
import java.sql.Connection;
import java.io.ByteArrayOutputStream;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EleveServiceImpl implements EleveService {
    final DataSource dataSource;
    final EleveRepository eleveRepository;

    @Override
    public byte[] listeEtudiant(Map<String, Object> parameters) throws Exception {

        try {

            String path = "src/main/resources/etat/template";
            File file = ResourceUtils.getFile(path + "/fichedeclasse.jrxml");
            // Charger le rapport
            JasperReport jasperreport = JasperCompileManager.compileReport(file.getAbsolutePath());

            File di = new File(path + "/depot_etat");
            boolean dir = di.mkdir();
            if (dir) {
                System.out.println("Dossier cree");
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperreport, parameters,
                    dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/depot_etat/fichedeclasse.pdf");
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
        Workbook workbook = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();

            // Exécuter la requête SQL (adaptez-la selon vos besoins)
            String sql = "SELECT e.Matri_Elev, e.Nom_Elev, e.Lieunais_Elev, e.Datenais_Elev, " +
                    "e.Sexe_Elev, e.celetud, n.Des_Nat, e.Code_Detcla, e.DateInscri_Eleve " +
                    "FROM \"Nationalité\" n " +
                    "INNER JOIN \"Elèves\" e ON e.\"Code_Nat\" = n.\"Code_Nat\" " +
                    "WHERE e.\"AnneeSco_Elev\" = '" + parameters.get("PARAMEANNE") + "' " +
                    "AND e.\"Code_Detcla\" LIKE '%" + parameters.get("PARAMCLASSE") + "%' " +
                    "AND e.\"DateInscri_Eleve\" BETWEEN '" + parameters.get("PARAMDATEDEBUT") + "' " +
                    "AND '" + parameters.get("PARAMDATEFIN") + "' " +
                    "UNION " +
                    "SELECT h.\"Matri_Elev\", h.\"Nom_Elev\", h.\"Lieunais_Elev\", h.\"Datenais_Elev\", " +
                    "h.\"Sexe_Elev\", h.celetud, n.\"Des_Nat\", h.\"Code_Detcla\", h.\"DateInscri_Eleve\" " +
                    "FROM \"Nationalité\" n " +
                    "INNER JOIN \"Historique\" h ON h.\"Code_Nat\" = n.\"Code_Nat\" " +
                    "WHERE h.\"AnneeSco_Elev\" = '" + parameters.get("PARAMEANNE") + "' " +
                    "AND h.\"Code_Detcla\" LIKE '%" + parameters.get("PARAMCLASSE") + "%' " +
                    "AND h.\"DateInscri_Eleve\" BETWEEN '" + parameters.get("PARAMDATEDEBUT") + "' " +
                    "AND '" + parameters.get("PARAMDATEFIN") + "' " +
                    "ORDER BY \"Code_Detcla\", \"Nom_Elev\"";

            rs = stmt.executeQuery(sql);

            // Créer un nouveau workbook Excel
            workbook = new XSSFWorkbook();
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
            String[] headers = { "Matricule", "Nom", "Lieu de naissance", "Date de naissance",
                    "Sexe", "Téléphone", "Nationalité", "Classe" };

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
                    dateStyle.setDataFormat((short) 14); // Format date court
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
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (out != null)
                    out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                if (workbook != null)
                    workbook.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<EleveRecordDTO> getPromotionsEleves(List<String> promotions, List<String> etablissements,
            String anneeScolaire, LocalDate dateDebut, LocalDate dateFin) throws Exception {
        String annSco = anneeScolaire.replace("-", "/");
        if (promotions == null || promotions.isEmpty() || etablissements == null || etablissements.isEmpty()) {
            return List.of(); // renvoie une liste vide pour éviter erreur SQL
        }
        log.info("Fetching students for promotions: {}, etablissements: {}, anneeScolaire: {}, dateDebut: {}, dateFin: {}",
                promotions, etablissements, annSco, dateDebut, dateFin);
        try (Stream<ELEVE> stream = eleveRepository.findValidElevesAsStream(promotions, etablissements, annSco,
                dateDebut, dateFin)) {
            return stream.map(e -> {
                String[] parts = e.getNomElev().trim().split("\\s+", 2);
                String nom = parts.length > 0 ? parts[0] : "";
                String prenoms = parts.length > 1 ? parts[1] : "";
                return new EleveRecordDTO(
                        nom,
                        prenoms,
                        e.getDatenaisElev(),
                        e.getSexeElev(),
                        e.getUnivmetiers(),
                        e.getCodeDetcla(),
                        e.getDateInscriEleve());
            }).toList();
        }
    }

    @Override
    public byte[] getPromotionsElevesExcel(List<String> promotions, List<String> etablissements, String anneeScolaire, LocalDate dateDebut, LocalDate dateFin)
            throws Exception {
        List<EleveRecordDTO> etudiants = getPromotionsEleves(promotions, etablissements, anneeScolaire,dateDebut,dateFin);
        String path = "src/main/resources/templates/ETUDIANTS.xlsx";
        File file = ResourceUtils.getFile(path);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (InputStream inputStream = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(inputStream); // ← plus robuste que new XSSFWorkbook
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.getSheetAt(0);

            int i = 3; // ligne de départ
            for (EleveRecordDTO etudiant : etudiants) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }

                // colonne 0 → Nom
                Cell cellNom = row.getCell(0);
                if (cellNom == null)
                    cellNom = row.createCell(0);
                cellNom.setCellValue(etudiant.getNom());

                // colonne 1 → Prénoms
                Cell cellPrenom = row.getCell(1);
                if (cellPrenom == null)
                    cellPrenom = row.createCell(1);
                cellPrenom.setCellValue(etudiant.getPrenoms());

                // colonne 2 → Date de naissance (convertir en texte ou en date Excel)
                Cell cellDate = row.getCell(2);
                if (cellDate == null)
                    cellDate = row.createCell(2);
                if (etudiant.getDateNaissance() != null) {
                    cellDate.setCellValue(etudiant.getDateNaissance().format(formatter).toString());
                }

                // colonne 3 → Sexe
                Cell cellSexe = row.getCell(3);
                if (cellSexe == null)
                    cellSexe = row.createCell(3);
                cellSexe.setCellValue(etudiant.getSexe());

                // colonne 4 → CodeDetcla
                Cell cellCode = row.getCell(4);
                if (cellCode == null)
                    cellCode = row.createCell(4);
                cellCode.setCellValue(etudiant.getCodeDetcla());

                // colonne 5 → Email personnel
                Cell cellEmail = row.getCell(5);
                if (cellEmail == null)
                    cellEmail = row.createCell(5);
                cellEmail.setCellValue(etudiant.getEmailPersonnel());

                Cell cellDest = row.getCell(6);
                if (cellDest == null)
                    cellDest = row.createCell(6);
                if (etudiant.getEmailPersonnel().length() > 0) {
                    cellDest.setCellValue("1");
                } else {
                    cellDest.setCellValue("0");
                }

                i++;
            }

            // Recalcul des formules (si tu en as)
            workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

            workbook.write(out);
            return out.toByteArray();
        }
    }

    @Override
    public List<String> getAllClasses(String anneeScolaire) throws Exception {
        String annSco = anneeScolaire.replace("-", "/");
        try (Stream<String> stream = eleveRepository.findValidCodeDetclaAsStream(annSco)) {
            return stream.toList();
        }
    }

}
