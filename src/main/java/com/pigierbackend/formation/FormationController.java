package com.pigierbackend.formation;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vi/formation")
public class FormationController {
    final FormationService formationService;
    @RequestMapping("/creerOrUpdateFormation")
    public FormationResponseDto createOrUpdateFormation(@RequestBody FormationRequestDto dto) {
        return formationService.createOrUpdateFormation(dto);
    }

    @RequestMapping("/deleteFormation/{id}")
    public Boolean deleteFormation(@PathVariable("id") Long id) {
        return formationService.deleteFormation(id);
    }

    @RequestMapping("/findAllFormation")
    public List<FormationResponseDto> findAllFormation() {
        return formationService.findAllFormation();
    }

}
