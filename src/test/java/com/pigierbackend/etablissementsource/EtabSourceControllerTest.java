
package com.pigierbackend.etablissementsource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Collections;
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

import com.fasterxml.jackson.databind.ObjectMapper;





public class EtabSourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EtabSourceService etabSourceService;

    @InjectMocks
    private EtabSourceController etabSourceController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc=MockMvcBuilders.standaloneSetup(etabSourceController).build();
    }

    @Test
    public void testCreerOrUpdateEtable() throws Exception {
        EtabSourceRequestDto requestDto = new EtabSourceRequestDto();
        EtabSourceResponseDto responseDto = new EtabSourceResponseDto();

        when(etabSourceService.creatOrUpdateEtabSource(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api/vi/etablissement/creerOrUpdateEtable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteEtab() throws Exception {
        Long id = 1L;

        when(etabSourceService.deleteEtabSource(id)).thenReturn(true);

        mockMvc.perform(delete("/api/vi/etablissement/deleteEtab/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllEtabSour() throws Exception {
        when(etabSourceService.findAllEtabSour()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/vi/etablissement/findAllEtabSour"))
                .andExpect(status().isOk());
    }
}