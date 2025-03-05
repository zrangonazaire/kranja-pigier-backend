package com.pigierbackend.formation;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;







public class FormationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FormationService formationService;

    @InjectMocks
    private FormationController formationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(formationController).build();
    }

    @Test
    public void testCreateOrUpdateFormation() throws Exception {
        FormationRequestDto requestDto = new FormationRequestDto();
        FormationResponseDto responseDto = new FormationResponseDto();

        when(formationService.createOrUpdateFormation(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api/vi/formation/creerOrUpdateFormation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"field\":\"value\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFormation() throws Exception {
        Long id = 1L;

        when(formationService.deleteFormation(id)).thenReturn(true);

        mockMvc.perform(delete("/api/vi/formation/deleteFormation/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllFormation() throws Exception {
        List<FormationResponseDto> responseDtoList = Arrays.asList(new FormationResponseDto(), new FormationResponseDto());

        when(formationService.findAllFormation()).thenReturn(responseDtoList);

        mockMvc.perform(get("/api/vi/formation/findAllFormation"))
                .andExpect(status().isOk());
    }
}