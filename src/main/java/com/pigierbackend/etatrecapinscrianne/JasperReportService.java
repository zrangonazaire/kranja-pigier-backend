package com.pigierbackend.etatrecapinscrianne;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class JasperReportService {
    
    private static final Logger logger = LoggerFactory.getLogger(JasperReportService.class);
    
    @Autowired
    private DataSource dataSource;
    
    public byte[] generateReport(Map<String, Object> parameters) throws JRException, SQLException {
        
        Connection connection = null;
        try {
            logger.info("Début génération du rapport");
            
            // Charger le fichier JRXML
            InputStream reportStream = getClass().getClassLoader()
                .getResourceAsStream("etat/template/recap.jrxml");
            
            if (reportStream == null) {
                throw new JRException("Fichier JRXML non trouvé: etat/template/recap.jrxml");
            }
            
            // Compiler le rapport
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            
            // Connexion à la base de données
            connection = dataSource.getConnection();
            
            // Remplir le rapport avec les paramètres
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, parameters, connection);
            
            // Exporter en PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            
            byte[] result = outputStream.toByteArray();
            logger.info("Rapport généré avec succès - Taille: {} bytes", result.length);
            
            return result;
            
        } finally {
            // Fermer la connexion
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.warn("Erreur lors de la fermeture de la connexion: {}", e.getMessage());
                }
            }
        }
    }
}