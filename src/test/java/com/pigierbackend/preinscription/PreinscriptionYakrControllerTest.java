package com.pigierbackend.preinscription;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreinscriptionYakrControllerTest {
    // @Autowired
    // MockMvc mockMvc;

    // @Mock
    // PreinscriptionService preinscriptionYakrService;

    // @InjectMocks
    // PreinscriptionController preinscriptionYakrController;

    // ObjectMapper objectMapper = new ObjectMapper();

    // @BeforeEach
    // public void setUp() {
    //     MockitoAnnotations.openMocks(this);
    //     mockMvc = MockMvcBuilders.standaloneSetup(preinscriptionYakrController).build();
    // }

    // @Test
    // void testCreerOrUpdateEtable() throws Exception {
    //     PreinscriptionRequestDto requestDto = new PreinscriptionRequestDto();
    //     PreinscriptionResponseDto responseDto = new PreinscriptionResponseDto();

    //     when(preinscriptionYakrService.createOrUpdatePreinscription(requestDto)).thenReturn(responseDto);

    //     mockMvc.perform(post("/preinscriptionyakro/creerOrUpdatePreinscYakro")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(requestDto)))
    //             .andExpect(status().isCreated());
    // }

    // @Test
    // void testDeleteEtab() throws Exception {
    //     String id = "1L";

    //     when(preinscriptionYakrService.deletePreinscription(id)).thenReturn(true);

    //     mockMvc.perform(delete("/preinscriptionyakro/deletePreinscYakro/{id}", id)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk());
    // }

    // @Test
    // void testFindAllEtab() throws Exception {
    //     mockMvc.perform(get("/preinscriptionyakro/findAllPreinscYakro/{page}/{size}", 0, 10)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk());
    // }

    // @Test
    // void testFindEtabById() throws Exception {
    //     String id = "1L";

    //     PreinscriptionResponseDto responseDto = new PreinscriptionResponseDto();

    //     when(preinscriptionYakrService.getPreinscriptionById(id)).thenReturn(responseDto);

    //     mockMvc.perform(get("/preinscriptionyakro/findPreinscYakroById/{id}",id)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk());
    // }

    // @Test
    // void testFindEtabByNom() throws Exception {
    //     String nomEleve = "nomEleve";

    //     mockMvc.perform(get("/preinscriptionyakro/findPreinscYakroByNomEleve/{nomEleve}", nomEleve)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk());
    // }
}
