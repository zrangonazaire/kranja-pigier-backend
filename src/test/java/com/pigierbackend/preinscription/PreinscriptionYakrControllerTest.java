package com.pigierbackend.preinscription;

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
