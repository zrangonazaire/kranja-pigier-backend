package com.pigierbackend.preinscriptionyakro;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preinscriptionyakro")
@Tag(name = "PréinscriptionYakro", description = "Préinscription Yakro Management APIs")
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreinscriptionYakrController {
    final PreinscriptionYakroService preinscriptionYakroService;

    @PostMapping("/creerOrUpdatePreinscYakro")
    public ResponseEntity<PreinscriptionYakroResponseDto> creerOrUpdatePreinscYakro(
            @RequestBody PreinscriptionYakroRequestDto dto) {
        return new ResponseEntity<PreinscriptionYakroResponseDto>(
                preinscriptionYakroService.createOrUpdatePreinscriptionYakro(dto), HttpStatus.CREATED);
    }
    @GetMapping(value = "/findAllPreinscYakro/{page}/{size}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PreinscriptionYakroResponseDto>> findAllPreinscYakro(@PathVariable("page") int page, @PathVariable("size") int size) {
        return new ResponseEntity<List<PreinscriptionYakroResponseDto>>(preinscriptionYakroService.getAllPreinscriptionYakro(page, size),
                HttpStatus.OK);
    }
    @DeleteMapping("/deletePreinscYakro/{id}")
    public ResponseEntity<Boolean> deletePreinscYakro(@PathVariable("id") String id) {
        return new ResponseEntity<Boolean>(preinscriptionYakroService.deletePreinscriptionYakro(id), HttpStatus.OK);
    }
    @GetMapping("/findPreinscYakroById/{id}")
    public ResponseEntity<PreinscriptionYakroResponseDto> findPreinscYakroById(@PathVariable("id") String id) {
        return new ResponseEntity<PreinscriptionYakroResponseDto>(preinscriptionYakroService.getPreinscriptionYakroById(id),
                HttpStatus.OK);
    }
    @GetMapping("/findPreinscYakroByNomEleve/{nomEleve}")
    public ResponseEntity<List<PreinscriptionYakroResponseDto>> findPreinscYakroByNomEleve(
            @PathVariable("nomEleve") String nomEleve) {
        return new ResponseEntity<List<PreinscriptionYakroResponseDto>>(
                preinscriptionYakroService.getPreinscriptionYakroByNomEleve(nomEleve), HttpStatus.OK);
    }

}
