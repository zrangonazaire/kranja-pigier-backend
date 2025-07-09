package com.pigierbackend.formation;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/formation")
@Tag(name = "FORMATION", description = "Formation Management APIs")
@CrossOrigin("*")
public class FormationController {
    final FormationService formationService;
    @PostMapping("/creerOrUpdateFormation")
    public FormationResponseDto createOrUpdateFormation(@RequestBody FormationRequestDto dto) {
        return formationService.createOrUpdateFormation(dto);
    }

    @DeleteMapping("/deleteFormation/{id}")
    public Boolean deleteFormation(@PathVariable Long id) {
        return formationService.deleteFormation(id);
    }

    @GetMapping("/findAllFormation")
    public List<FormationResponseDto> findAllFormation() {
        return formationService.findAllFormation();
    }
    

}
