package com.pigierbackend.encaissement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.*;

@DependsOnDatabaseInitialization
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class EncaissementServiceImpl implements EncaissementService {

    final DataSource dataSource;

    // Mapping niveau → row Excel
    private static final Map<String, Integer> NIVEAU_TO_ROW = Map.of(
            "Licence 1", 7,
            "Licence 2", 8,
            "Licence 3", 9,
            "Master 1", 10,
            "Master 2", 11
    );

    // Mapping mois → colonne feuille 1
    private static final Map<Integer, Integer> MOIS_TO_COL_FEUILLE1 = Map.of(
            1, 2, 2, 3, 3, 4, 4, 6, 5, 7, 6, 8
    );

    // Mapping mois → colonne feuille 2
    private static final Map<Integer, Integer> MOIS_TO_COL_FEUILLE2 = Map.of(
            7, 2, 8, 3, 9, 4, 10, 6, 11, 7, 12, 8
    );

    /** ==================== RAPPORTS JASPER ==================== **/

    private byte[] generateJasperReport(String jrxmlFileName, String outputPdfName, Map<String, Object> parameters) {
        String templatePath = "src/main/resources/etat/template";
        try {
            File file = ResourceUtils.getFile(templatePath + "/" + jrxmlFileName);
            log.info("Chargement du rapport Jasper : {}", file.getAbsolutePath());

            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            File depotDir = new File(templatePath + "/depot_etat");
            if (depotDir.mkdirs()) {
                log.info("Dossier 'depot_etat' créé");
            }

            try (Connection conn = dataSource.getConnection()) {
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
                JasperExportManager.exportReportToPdfFile(jasperPrint, depotDir + "/" + outputPdfName);
                return JasperExportManager.exportReportToPdf(jasperPrint);
            }

        } catch (Exception e) {
            log.error("Erreur lors de la génération du rapport Jasper", e);
            throw new RuntimeException("Erreur génération rapport Jasper: " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] generateJournalEncaissementsBetweenDatesReport(Map<String, Object> parameters) {
        return generateJasperReport(
                "point_encaissement_tout_par_caisse_deux_periode.jrxml",
                "poindecaisseentredeuxdate.pdf",
                parameters
        );
    }

    @Override
    public byte[] generateJournalDroitInscrisBetweenDatesReport(Map<String, Object> parameters) {
        return generateJasperReport(
                "point_encaissement_doit_inscrit_par_caisse_deux_periode.jrxml",
                "poindecaissedroitinscentredeuxdate.pdf",
                parameters
        );
    }

    /** ==================== RAPPORT EXCEL ==================== **/

    @Override
    public byte[] generateEtatFacturationReport(Map<String, Object> parameters) throws Exception {
        List<EncaissementDTO> encaissements = getEncaissementsEntreDeuxPeriodeEtabSource(parameters);
        String path = "src/main/resources/templates";
        File file = ResourceUtils.getFile(path + "/facturation.xlsx");

        try (InputStream inputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(inputStream);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            for (EncaissementDTO encaissement : encaissements) {
                String niveau = encaissement.getNiveauLibelle();
                int mois = encaissement.getMois();
                Integer rowIndex = NIVEAU_TO_ROW.get(niveau);
                Integer colIndex;

                Sheet sheet;
                if (mois <= 6) {
                    sheet = workbook.getSheetAt(0);
                    colIndex = MOIS_TO_COL_FEUILLE1.get(mois);
                } else {
                    sheet = workbook.getSheetAt(1);
                    colIndex = MOIS_TO_COL_FEUILLE2.get(mois);
                }

                if (rowIndex != null && colIndex != null) {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null) row = sheet.createRow(rowIndex);
                    Cell cell = row.getCell(colIndex);
                    if (cell == null) cell = row.createCell(colIndex);
                    cell.setCellValue(encaissement.getSommeMontantEncais().doubleValue());
                }
            }

            workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
            workbook.write(out);
            return out.toByteArray();
        }
    }

    /** ==================== REQUÊTE SQL ==================== **/

    //@SuppressWarnings("unchecked")
    public List<EncaissementDTO> getEncaissementsEntreDeuxPeriodeEtabSource(Map<String, Object> parameters) {
        String sql = "WITH Payments AS (" +
                "SELECT e.Date_Encais, e.Montant_Encais, e.FraisExamen, c.Niveau_grade " +
                "FROM [Encaissements des Elèves Pl] e " +
                "INNER JOIN Elèves el ON e.Matri_Elev = el.Matri_Elev " +
                "INNER JOIN [Détail Classes] d ON el.Code_Detcla = d.Code_Detcla " +
                "INNER JOIN [Classes Info Générales] c ON d.Code_Cla = c.Code_Cla " +
                "WHERE e.Date_Encais >= ? AND e.Date_Encais <= ?  " +
                "AND e.Etablissement_Source IN (%s) " +
                "UNION ALL " +
                "SELECT h.Date_Encais, h.Montant_Encais, h.FraisExamen, c.Niveau_grade " +
                "FROM Historique_Encaissement_Pl h " +
                "INNER JOIN Historique hist ON h.Matri_Elev = hist.Matri_Elev " +
                "INNER JOIN [Détail Classes] d ON hist.Code_Detcla = d.Code_Detcla " +
                "INNER JOIN [Classes Info Générales] c ON d.Code_Cla = c.Code_Cla " +
                "WHERE h.Date_Encais >= ? AND h.Date_Encais <= ?  " +
                "AND h.Etablissement_Source IN (%s) " +
                ")" +
                "SELECT SUM(COALESCE(p.Montant_Encais,0) ) AS sommeMontantEncais, " +
                "DATEPART(month, p.Date_Day) AS mois, " +
                "DATEPART(year, p.Date_Day) AS annee, " +
                "DATEPART(quarter, p.Date_Day) AS trimestre, " +
                "CASE p.Niveau_grade " +
                "WHEN 'L1' THEN 'Licence 1' " +
                "WHEN 'L2' THEN 'Licence 2' " +
                "WHEN 'B1' THEN 'Licence 1' " +
                "WHEN 'B2' THEN 'Licence 2' " +
                "WHEN 'L3' THEN 'Licence 3' " +
                "WHEN 'M1' THEN 'Master 1' " +
                "WHEN 'M2' THEN 'Master 2' ELSE 'Autre' END AS niveauLibelle " +
                "FROM (SELECT CAST(Date_Encais AS DATE) AS Date_Day, Niveau_grade, Montant_Encais, FraisExamen FROM Payments) p " +
                "GROUP BY DATEPART(year, p.Date_Day), DATEPART(month, p.Date_Day), DATEPART(quarter, p.Date_Day), p.Niveau_grade " +
                "ORDER BY annee, mois";

        List<String> etablissementSources = (List<String>) parameters.get("etablissementSources");
        String placeholders = buildPlaceholders(etablissementSources.size());
        sql = String.format(sql, placeholders, placeholders);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            java.util.Date dateDebutUtil = (java.util.Date) parameters.get("dateDebut");
            java.util.Date dateFinUtil = (java.util.Date) parameters.get("dateFin");
            java.sql.Date dateDebut = new java.sql.Date(dateDebutUtil.getTime());
            java.sql.Date dateFin = new java.sql.Date(dateFinUtil.getTime());

            int paramIndex = 1;
            stmt.setDate(paramIndex++, dateDebut);
            stmt.setDate(paramIndex++, dateFin);
            for (String etab : etablissementSources) stmt.setString(paramIndex++, etab);
            stmt.setDate(paramIndex++, dateDebut);
            stmt.setDate(paramIndex++, dateFin);
            for (String etab : etablissementSources) stmt.setString(paramIndex++, etab);

            List<EncaissementDTO> result = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(EncaissementDTO.builder()
                            .sommeMontantEncais(rs.getBigDecimal("sommeMontantEncais"))
                            .mois(rs.getInt("mois"))
                            .annee(rs.getInt("annee"))
                            .trimestre(rs.getInt("trimestre"))
                            .niveauLibelle(rs.getString("niveauLibelle"))
                            .build());
                }
            }
            return result;

        } catch (SQLException e) {
            log.error("Erreur lors de l'exécution de la requête SQL", e);
            throw new RuntimeException("Erreur SQL: " + e.getMessage(), e);
        }
    }

    /** ==================== UTILS ==================== **/

    private String buildPlaceholders(int count) {
        if (count <= 0) return "NULL";
        return String.join(",", Collections.nCopies(count, "?"));
    }
}
