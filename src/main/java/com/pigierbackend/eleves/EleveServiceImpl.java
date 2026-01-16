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
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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
            System.out.println("Erreur lors de la gÃ©nÃ©ration du rapport : " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Erreur: " + e.getMessage());
        } finally {
            // Fermer les ressources si nÃ©cessaire
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

            // ExÃ©cuter la requÃªte SQL (adaptez-la selon vos besoins)
            String sql = "SELECT e.Matri_Elev, e.Nom_Elev, e.Lieunais_Elev, e.Datenais_Elev, " +
                    "e.Sexe_Elev, e.celetud, n.Des_Nat, e.Code_Detcla, e.DateInscri_Eleve " +
                    "FROM \"NationalitÃ©\" n " +
                    "INNER JOIN \"ElÃ¨ves\" e ON e.\"Code_Nat\" = n.\"Code_Nat\" " +
                    "WHERE e.\"AnneeSco_Elev\" = '" + parameters.get("PARAMEANNE") + "' " +
                    "AND e.\"Code_Detcla\" LIKE '%" + parameters.get("PARAMCLASSE") + "%' " +
                    "AND e.\"DateInscri_Eleve\" BETWEEN '" + parameters.get("PARAMDATEDEBUT") + "' " +
                    "AND '" + parameters.get("PARAMDATEFIN") + "' " +
                    "UNION " +
                    "SELECT h.\"Matri_Elev\", h.\"Nom_Elev\", h.\"Lieunais_Elev\", h.\"Datenais_Elev\", " +
                    "h.\"Sexe_Elev\", h.celetud, n.\"Des_Nat\", h.\"Code_Detcla\", h.\"DateInscri_Eleve\" " +
                    "FROM \"NationalitÃ©\" n " +
                    "INNER JOIN \"Historique\" h ON h.\"Code_Nat\" = n.\"Code_Nat\" " +
                    "WHERE h.\"AnneeSco_Elev\" = '" + parameters.get("PARAMEANNE") + "' " +
                    "AND h.\"Code_Detcla\" LIKE '%" + parameters.get("PARAMCLASSE") + "%' " +
                    "AND h.\"DateInscri_Eleve\" BETWEEN '" + parameters.get("PARAMDATEDEBUT") + "' " +
                    "AND '" + parameters.get("PARAMDATEFIN") + "' " +
                    "ORDER BY \"Code_Detcla\", \"Nom_Elev\"";

            rs = stmt.executeQuery(sql);

            // CrÃ©er un nouveau workbook Excel
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Liste des Ã‰lÃ¨ves");

            // CrÃ©er le style pour l'en-tÃªte
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);

            // CrÃ©er la ligne d'en-tÃªte
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Matricule", "Nom", "Lieu de naissance", "Date de naissance",
                    "Sexe", "TÃ©lÃ©phone", "NationalitÃ©", "Classe" };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Remplir les donnÃ©es
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

            // Ã‰crire le workbook dans le ByteArrayOutputStream
            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            System.out.println("Erreur lors de la gÃ©nÃ©ration du fichier Excel : " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Erreur lors de la gÃ©nÃ©ration Excel: " + e.getMessage());
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

//    @Override
//    public List<EleveRecordDTO> getPromotionsEleves(List<String> promotions, List<String> etablissements,
//            String anneeScolaire, LocalDate dateDebut, LocalDate dateFin) throws Exception {
//        String annSco = anneeScolaire.replace("-", "/").trim();
//        List<String> normalizedPromotions = promotions == null ? List.of()
//                : promotions.stream()
//                        .filter(Objects::nonNull)
//                        .map(String::trim)
//                        .filter(value -> !value.isEmpty())
//                        .map(value -> value.toUpperCase(Locale.ROOT))
//                        .toList();
//        List<String> normalizedEtablissements = etablissements == null ? List.of()
//                : etablissements.stream()
//                        .filter(Objects::nonNull)
//                        .map(String::trim)
//                        .filter(value -> !value.isEmpty())
//                        .map(value -> value.toUpperCase(Locale.ROOT))
//                        .toList();
//        if (normalizedPromotions.isEmpty() || normalizedEtablissements.isEmpty()) {
//            return List.of(); // renvoie une liste vide pour Ç¸viter erreur SQL
//        }
//        log.info(
//                "Fetching students for promotions: {}, etablissements: {}, anneeScolaire: {}, dateDebut: {}, dateFin: {}",
//                normalizedPromotions, normalizedEtablissements, annSco, dateDebut, dateFin);
//        if (normalizedPromotions.size() == 1) {
//            String promotion = normalizedPromotions.get(0);
//            if (promotion.contains("*") || promotion.contains("%")) {
//                String likePattern = promotion.replace("*", "%");
//                try (Stream<ELEVE> stream = eleveRepository.findValidElevesByPromotionPatternAsStream(likePattern,
//                        normalizedEtablissements, annSco, dateDebut, dateFin)) {
//                    return mapEleves(stream);
//                }
//            }
//        }
//        try (Stream<ELEVE> stream = eleveRepository.findValidElevesAsStream(normalizedPromotions,
//                normalizedEtablissements, annSco, dateDebut, dateFin)) {
//            return mapEleves(stream);
//        }
//    }

    @Override
    public List<EleveRecordDTO> getPromotionsEleves(List<String> promotions,
                                                    List<String> etablissements,
                                                    String anneeScolaire,
                                                    LocalDate dateDebut,
                                                    LocalDate dateFin) throws Exception {

        String annSco = anneeScolaire.replace("-", "/").trim();

        List<String> normalizedPromotions = promotions == null ? List.of()
                : promotions.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(v -> !v.isEmpty())
                .map(v -> v.toUpperCase(Locale.ROOT))
                .toList();

        List<String> normalizedEtablissements = etablissements == null ? List.of()
                : etablissements.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(v -> !v.isEmpty())
                .map(v -> v.toUpperCase(Locale.ROOT))
                .toList();

        if (normalizedPromotions.isEmpty() || normalizedEtablissements.isEmpty()) {
            return List.of();
        }

        log.info("Fetching students for promotions: {}, etablissements: {}, anneeScolaire: {}, dateDebut: {}, dateFin: {}",
                normalizedPromotions, normalizedEtablissements, annSco, dateDebut, dateFin);

        // Gestion wildcard si une seule promotion
        if (normalizedPromotions.size() == 1) {
            String promotion = normalizedPromotions.get(0);
            if (promotion.contains("*") || promotion.contains("%")) {
                String likePattern = promotion.replace("*", "%");
                try (Stream<ELEVE> stream =
                             eleveRepository.findValidElevesByPromotionPatternAsStreamByEncaissementDate(
                                     likePattern, normalizedEtablissements, annSco, dateDebut, dateFin
                             )) {
                    return mapEleves(stream);
                }
            }
        }

        try (Stream<ELEVE> stream =
                     eleveRepository.findValidElevesAsStreamByEncaissementDate(
                             normalizedPromotions, normalizedEtablissements, annSco, dateDebut, dateFin
                     )) {
            return mapEleves(stream);
        }
    }


    private List<EleveRecordDTO> mapEleves(Stream<ELEVE> stream) {
        return stream.map(e -> {
            String[] parts = e.getNomElev().trim().split("\\s+", 2);
            String nom = parts.length > 0 ? parts[0] : "";
            String prenoms = parts.length > 1 ? parts[1] : "";
            return new EleveRecordDTO(
                    e.getMatriElev(),
                    nom,
                    prenoms,
                    e.getDatenaisElev(),
                    e.getLieunaisElev(),
                    e.getSexeElev(),
                    e.getUnivmetiers(),
                    e.getCodeDetcla(),
                    e.getCeletud().isEmpty() ? e.getTeletud() : e.getCeletud(),
                    e.getTelBurRespElev().isEmpty() ? e.getTelDomRespElev() : e.getTelBurRespElev(),
                    e.getDateInscriEleve());
        }).toList();
    }

    @Override
    public byte[] getPromotionsElevesExcel(List<String> promotions,
                                           List<String> etablissements,
                                           String anneeScolaire,
                                           LocalDate dateDebut,
                                           LocalDate dateFin) throws Exception {

        List<EleveRecordDTO> etudiants = getPromotionsEleves(
                promotions, etablissements, anneeScolaire, dateDebut, dateFin
        );

        String path = "src/main/resources/templates/ETUDIANTS.xlsx";
        File file = ResourceUtils.getFile(path);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (InputStream inputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(inputStream);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.getSheetAt(0);

            int i = 3; // ligne de dÃ©part (aprÃ¨s l'entÃªte)

            for (EleveRecordDTO etudiant : etudiants) {

                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }

                // 0 â†’ Matricule
                Cell cellMatricule = row.getCell(0);
                if (cellMatricule == null) cellMatricule = row.createCell(0);
                cellMatricule.setCellValue(
                        etudiant.getMatriElev() != null ? etudiant.getMatriElev() : ""
                );

                // 1 â†’ Nom
                Cell cellNom = row.getCell(1);
                if (cellNom == null) cellNom = row.createCell(1);
                cellNom.setCellValue(etudiant.getNom());

                // 2 â†’ PrÃ©noms
                Cell cellPrenom = row.getCell(2);
                if (cellPrenom == null) cellPrenom = row.createCell(2);
                cellPrenom.setCellValue(etudiant.getPrenoms());

                // 3 â†’ Date de naissance
                Cell cellDateNaiss = row.getCell(3);
                if (cellDateNaiss == null) cellDateNaiss = row.createCell(3);
                if (etudiant.getDateNaissance() != null) {
                    cellDateNaiss.setCellValue(
                            etudiant.getDateNaissance().format(formatter)
                    );
                }

                // 4 â†’ Lieu de naissance
                Cell cellLieuNaiss = row.getCell(4);
                if (cellLieuNaiss == null) cellLieuNaiss = row.createCell(4);
                cellLieuNaiss.setCellValue(
                        etudiant.getLieunaisElev() != null ? etudiant.getLieunaisElev() : ""
                );

                // 5 â†’ Sexe
                Cell cellSexe = row.getCell(5);
                if (cellSexe == null) cellSexe = row.createCell(5);
                cellSexe.setCellValue(etudiant.getSexe());

                // 6 â†’ Promotions
                Cell cellPromo = row.getCell(6);
                if (cellPromo == null) cellPromo = row.createCell(6);
                cellPromo.setCellValue(etudiant.getCodeDetcla());

                // 7 â†’ E-mail institutionnel
                Cell cellEmail = row.getCell(7);
                if (cellEmail == null) cellEmail = row.createCell(7);
                cellEmail.setCellValue(
                        etudiant.getEmailPersonnel() != null ? etudiant.getEmailPersonnel() : ""
                );

                // 8 â†’ NumÃ©ro Ã©tudiant
                Cell cellTelEleve = row.getCell(8);
                if (cellTelEleve == null) cellTelEleve = row.createCell(8);
                cellTelEleve.setCellValue(
                        etudiant.getTelEleve() != null ? etudiant.getTelEleve() : ""
                );

                // 9 â†’ NumÃ©ro parent
                Cell cellTelParent = row.getCell(9);
                if (cellTelParent == null) cellTelParent = row.createCell(9);
                cellTelParent.setCellValue(
                        etudiant.getTelParent() != null ? etudiant.getTelParent() : ""
                );

                i++;
            }

            workbook.getCreationHelper()
                    .createFormulaEvaluator()
                    .evaluateAll();

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

    @Override
    public List<EleveRecordAvecPayerDto> getPromotionsElevesAvecMontantPayer(List<String> promotions,
            List<String> etablissements, String anneeScolaire, LocalDate dateDebut, LocalDate dateFin, int montantDu)
            throws Exception {
        String annSco = anneeScolaire.replace("-", "/");
        if (promotions == null || promotions.isEmpty() || etablissements == null || etablissements.isEmpty()) {
            return List.of(); // renvoie une liste vide pour Ã©viter erreur SQL
        }
        log.info(
                "Fetching students for promotions: {}, etablissements: {}, anneeScolaire: {}, dateDebut: {}, dateFin: {}",
                promotions, etablissements, annSco, dateDebut, dateFin);
        try (Stream<ELEVE> stream = eleveRepository.findValidElevesAvecMontantAsStream(promotions, etablissements, annSco,
                dateDebut, dateFin, montantDu)) {
            return stream.map(e -> {
                String[] parts = e.getNomElev().trim().split("\\s+", 2);
                String nom = parts.length > 0 ? parts[0] : "";
                String prenoms = parts.length > 1 ? parts[1] : "";
                // Correction ici : conversion en BigDecimal
                java.math.BigDecimal montantScoElev = java.math.BigDecimal.valueOf(e.getMontantScoElev());
                java.math.BigDecimal droitInscription = java.math.BigDecimal.valueOf(e.getDroitinscription());
                java.math.BigDecimal soldScoElev = java.math.BigDecimal.valueOf(e.getSoldScoElev());
                int montantPayer = montantScoElev.add(droitInscription).subtract(soldScoElev).intValue();
                return new EleveRecordAvecPayerDto(
                        e.getMatriElev(),
                        nom,
                        prenoms,
                        e.getDatenaisElev(),
                        e.getLieunaisElev(),
                        e.getSexeElev(),
                        e.getUnivmetiers(),
                        e.getCodeDetcla(),
                        e.getCeletud().isEmpty()?e.getTeletud():e.getCeletud(),
                        e.getTelBurRespElev().isEmpty()?e.getTelDomRespElev():e.getTelBurRespElev(),
                        montantPayer,
                        e.getDateInscriEleve());
            }).toList();
        }
    }

    public byte[] getPromotionsElevesExcels(List<String> promotions,
                                           List<String> etablissements,
                                           String anneeScolaire,
                                           LocalDate dateDebut,
                                           LocalDate dateFin) throws Exception {

        List<EleveRecordDTO> etudiants = getPromotionsEleves(
                promotions, etablissements, anneeScolaire, dateDebut, dateFin
        );

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Ã‰lÃ¨ves");

            // =========================
            // Styles
            // =========================
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);

            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(
                    creationHelper.createDataFormat().getFormat("dd/MM/yyyy")
            );

            // =========================
            // EntÃªtes
            // =========================
            String[] headers = {
                    "Matricule",
                    "Nom",
                    "PrÃ©nom",
                    "NÃ©(e) le",
                    "Lieu de naissance",
                    "Sexe",
                    "Promotion",
                    "E-mail institutionnel",
                    "TÃ©lÃ©phone Ã©tudiant",
                    "TÃ©lÃ©phone parent"
            };

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                cell.setCellStyle(headerStyle);
            }

            // =========================
            // DonnÃ©es
            // =========================
            int rowIdx = 1;
            for (EleveRecordDTO e : etudiants) {

                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(nullSafe(e.getMatriElev()));
                row.createCell(1).setCellValue(nullSafe(e.getNom()));
                row.createCell(2).setCellValue(nullSafe(e.getPrenoms()));

                // Date de naissance
                Cell dateCell = row.createCell(3);
                if (e.getDateNaissance() != null) {
                    dateCell.setCellValue(
                            java.sql.Timestamp.valueOf(e.getDateNaissance())
                    );
                    dateCell.setCellStyle(dateStyle);
                }

                row.createCell(4).setCellValue(nullSafe(e.getLieunaisElev()));
                row.createCell(5).setCellValue(nullSafe(e.getSexe()));
                row.createCell(6).setCellValue(nullSafe(e.getCodeDetcla()));
                row.createCell(7).setCellValue(nullSafe(e.getEmailPersonnel()));
                row.createCell(8).setCellValue(nullSafe(e.getTelEleve()));
                row.createCell(9).setCellValue(nullSafe(e.getTelParent()));
            }

            // =========================
            // Auto-size
            // =========================
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }


}

