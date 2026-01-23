package com.pigierbackend.preinscription;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.sf.jasperreports.engine.JRException;

import org.springframework.http.HttpHeaders;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preinscription")
@Tag(name = "PrÃ©inscription", description = "PrÃ©inscription Management APIs")
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreinscriptionController {
    final PreinscriptionService preinscriptionYakroService;

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('WRITE_PREINSCRIPTION')")
    @PostMapping(value = "/creerOrUpdatePreinsc", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PreinscriptionResponseDto> creerOrUpdatePreinsc(
            @RequestBody PreinscriptionRequestDto dto) {
        return new ResponseEntity<PreinscriptionResponseDto>(
                preinscriptionYakroService.createOrUpdatePreinscription(dto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ_PREINSCRIPTION')")
    @GetMapping(value = "/findAllPreinscsansparam", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PreinscriptionResponseDto>> findAllPreinscSansParam() {
        return new ResponseEntity<List<PreinscriptionResponseDto>>(
                preinscriptionYakroService.getAllPreinscription(),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ_PREINSCRIPTION')")
    @GetMapping(value = "/findAllPreinscEntreDeuxDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PreinscriptionResponseDto>> findAllPreinscEntreDeuxDate(
            @RequestParam  String debut,
            @RequestParam  String fin) {
                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    
                LocalDateTime dateDebut = LocalDateTime.parse(debut, formatter);
                LocalDateTime dateFin = LocalDateTime.parse(fin, formatter);
        return new ResponseEntity<List<PreinscriptionResponseDto>>(
                preinscriptionYakroService.getAllPreinscriptionEntreDeuxDate(dateDebut, dateFin),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ_PREINSCRIPTION')")
    @GetMapping(value = "/findAllPreinsc/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PreinscriptionResponseDto>> findAllPreinsc(
            @PathVariable int size) {
        return new ResponseEntity<List<PreinscriptionResponseDto>>(
                preinscriptionYakroService.getAllPreinscription(size),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ_PREINSCRIPTION')")
    @DeleteMapping("/deletePreinsc/{id}")
    public ResponseEntity<Boolean> deletePreinscYakro(@PathVariable String id) {
        return new ResponseEntity<Boolean>(preinscriptionYakroService.deletePreinscription(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ_PREINSCRIPTION')")
    @GetMapping("/findPreinscById/{id}")
    public ResponseEntity<PreinscriptionResponseDto> findPreinscYakroById(@PathVariable String id) {
        return new ResponseEntity<PreinscriptionResponseDto>(
                preinscriptionYakroService.getPreinscriptionById(id),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ_PREINSCRIPTION')")
    @GetMapping("/findPreinscByNomEleve/{nomEleve}")
    public ResponseEntity<List<PreinscriptionResponseDto>> findPreinscYakroByNomEleve(
            @PathVariable String nomEleve) {
        return new ResponseEntity<List<PreinscriptionResponseDto>>(
                preinscriptionYakroService.getPreinscriptionByNomEleve(nomEleve), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ_PREINSCRIPTION')")
    @GetMapping("/impressionPreinscription/{id}")
    public ResponseEntity<byte[]> impressionPreinscriptionYakro(@PathVariable String id)
            throws FileNotFoundException, JRException, SQLException {
        byte[] reportBytes = preinscriptionYakroService.impressionPreinscription(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "fichpreinscription.pdf"); // Nom du fichier

        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ_PREINSCRIPTION')")
    @GetMapping("/impressionInscription/{id}")
    public ResponseEntity<byte[]> impressionInscriptionYakro(@PathVariable String id)
            throws FileNotFoundException, JRException, SQLException {
        byte[] reportBytes = preinscriptionYakroService.impressionInscription(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "ficheinscription.pdf"); // Nom du fichier

        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('READ_PREINSCRIPTION')")
    @GetMapping("/impressionFicheMedicale/{id}")
    public ResponseEntity<byte[]> impressionFicheMedicaleyakro(@PathVariable String id)
            throws FileNotFoundException, JRException, SQLException {
        byte[] reportBytes = preinscriptionYakroService.impressionFicheMedicale(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "fichemedical.pdf"); // Nom du fichier

        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);
    }

}
