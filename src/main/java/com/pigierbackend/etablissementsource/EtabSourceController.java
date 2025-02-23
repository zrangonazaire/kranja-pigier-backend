package com.pigierbackend.etablissementsource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("etablissement")
@Tag(name = "Etablissement", description = "Etablissement management APIs")

@FieldDefaults(level = AccessLevel.PRIVATE)
public class EtabSourceController {
    final EtabSourceService etabSourceService;
    @Operation(summary = "Creation e mise a jour d'un etablisemnt", description = "Authenticates a user and returns a JWT token")
 
    @PostMapping("/creerOrUpdateEtable")
    public ResponseEntity<EtabSourceResponseDto> creerOrUpdateEtable(@RequestBody EtabSourceRequestDto dto) {
        return new ResponseEntity<EtabSourceResponseDto>(etabSourceService.creatOrUpdateEtabSource(dto),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteEtab/{id}")
    public ResponseEntity<Boolean> deleteEtab(@PathVariable("id") Long id) {
        return new ResponseEntity<Boolean>(etabSourceService.deleteEtabSource(id), HttpStatus.OK);
    }

    @GetMapping("/findAllEtabSour")
    public ResponseEntity<List<EtabSourceResponseDto>> findAllEtabSour() {
        return new ResponseEntity<List<EtabSourceResponseDto>>(etabSourceService.findAllEtabSour(), HttpStatus.OK);
    }
}
