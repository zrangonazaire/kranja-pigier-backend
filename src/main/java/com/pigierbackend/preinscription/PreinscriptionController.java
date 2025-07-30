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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preinscription")
@Tag(name = "Préinscription", description = "Préinscription Management APIs")
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreinscriptionController {
    final PreinscriptionService preinscriptionYakroService;

    @PostMapping(value = "/creerOrUpdatePreinsc", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//     @PreAuthorize("hasAuthority('WRITE_PREINSCRIPTION')")
    public ResponseEntity<PreinscriptionResponseDto> creerOrUpdatePreinscYakro(
            @RequestBody PreinscriptionRequestDto dto) {
        return new ResponseEntity<PreinscriptionResponseDto>(
                preinscriptionYakroService.createOrUpdatePreinscription(dto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/findAllPreinscsansparam", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAuthority('READ_PREINSCRIPTION')")
    public ResponseEntity<List<PreinscriptionResponseDto>> findAllPreinscSansParam() {
        return new ResponseEntity<List<PreinscriptionResponseDto>>(
                preinscriptionYakroService.getAllPreinscription(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/findAllPreinscEntreDeuxDate/{debut}/{fin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PreinscriptionResponseDto>> findAllPreinscEntreDeuxDate(
            @PathVariable String debut, @PathVariable String fin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateDebut = LocalDateTime.parse(debut, formatter);
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime dateFin = LocalDateTime.parse(fin, formatter);
        return new ResponseEntity<List<PreinscriptionResponseDto>>(
                preinscriptionYakroService.getAllPreinscriptionEntreDeuxDate(dateDebut, dateFin),
                HttpStatus.OK);
    }

    @GetMapping(value = "/findAllPreinsc/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
 
    //@PreAuthorize("hasAuthority('READ_PREINSCRIPTION')")
    public ResponseEntity<List<PreinscriptionResponseDto>> findAllPreinsc(
            @PathVariable int size) {
        return new ResponseEntity<List<PreinscriptionResponseDto>>(
                preinscriptionYakroService.getAllPreinscription(size),
                HttpStatus.OK);
    }

    @DeleteMapping("/deletePreinsc/{id}")
   
   // @PreAuthorize("hasAuthority('DELETE_PREINSCRIPTION')")
    public ResponseEntity<Boolean> deletePreinscYakro(@PathVariable String id) {
        return new ResponseEntity<Boolean>(preinscriptionYakroService.deletePreinscription(id), HttpStatus.OK);
    }

    @GetMapping("/findPreinscById/{id}")
  
   // @PreAuthorize("hasAuthority('READ_PREINSCRIPTION')")
    public ResponseEntity<PreinscriptionResponseDto> findPreinscYakroById(@PathVariable String id) {
        return new ResponseEntity<PreinscriptionResponseDto>(
                preinscriptionYakroService.getPreinscriptionById(id),
                HttpStatus.OK);
    }

    @GetMapping("/findPreinscByNomEleve/{nomEleve}")
 
  //  @PreAuthorize("hasAuthority('READ_PREINSCRIPTION')")
    public ResponseEntity<List<PreinscriptionResponseDto>> findPreinscYakroByNomEleve(
            @PathVariable String nomEleve) {
        return new ResponseEntity<List<PreinscriptionResponseDto>>(
                preinscriptionYakroService.getPreinscriptionByNomEleve(nomEleve), HttpStatus.OK);
    }

    @GetMapping("/impressionPreinscription/{id}")
    //@PreAuthorize("hasAuthority('READ_PREINSCRIPTION')")
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

    @GetMapping("/impressionInscription/{id}")
   // @PreAuthorize("hasAuthority('READ_PREINSCRIPTION')")
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

    @GetMapping("/impressionFicheMedicale/{id}")
   
   // @PreAuthorize("hasAuthority('READ_PREINSCRIPTION')")
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
