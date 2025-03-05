package com.pigierbackend.etablissementsource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;






public class EtabSourceServiceImplTest {

    @Mock
    private EtabSourceRepository etabSourceRepository;

    @Mock
    private EtabSourceMapper etabSourceMapper;

    @InjectMocks
    private EtabSourceServiceImpl etabSourceServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatOrUpdateEtabSource() {
        EtabSourceRequestDto dto = new EtabSourceRequestDto();
        dto.setId(1L);
        dto.setLibEtabSource("Test Source");

        EtabSource etabSource = new EtabSource();
        etabSource.setId(1L);
        etabSource.setLibEtabSource("Test Source");

        when(etabSourceRepository.findById(dto.getId())).thenReturn(Optional.of(etabSource));
        when(etabSourceRepository.save(any(EtabSource.class))).thenReturn(etabSource);
        when(etabSourceMapper.fromEtabSource(any(EtabSource.class))).thenReturn(new EtabSourceResponseDto());

        EtabSourceResponseDto response = etabSourceServiceImpl.creatOrUpdateEtabSource(dto);

        assertNotNull(response);
        verify(etabSourceRepository, times(1)).findById(dto.getId());
        verify(etabSourceRepository, times(1)).save(any(EtabSource.class));
        verify(etabSourceMapper, times(1)).fromEtabSource(any(EtabSource.class));
    }

    @Test
    public void testDeleteEtabSource() {
        Long id = 1L;
        EtabSource etabSource = new EtabSource();
        etabSource.setId(id);

        when(etabSourceRepository.findById(id)).thenReturn(Optional.of(etabSource));

        Boolean result = etabSourceServiceImpl.deleteEtabSource(id);

        assertTrue(result);
        verify(etabSourceRepository, times(1)).findById(id);
        verify(etabSourceRepository, times(1)).delete(etabSource);
    }

    @Test
    public void testDeleteEtabSource_NotFound() {
        Long id = 1L;

        when(etabSourceRepository.findById(id)).thenReturn(Optional.empty());

        Boolean result = etabSourceServiceImpl.deleteEtabSource(id);

        assertFalse(result);
        verify(etabSourceRepository, times(1)).findById(id);
        verify(etabSourceRepository, never()).delete(any(EtabSource.class));
    }

    @Test
    public void testFindAllEtabSour() {
        EtabSource etabSource1 = new EtabSource();
        etabSource1.setLibEtabSource("Source A");
        EtabSource etabSource2 = new EtabSource();
        etabSource2.setLibEtabSource("Source B");

        when(etabSourceRepository.findAll()).thenReturn(Arrays.asList(etabSource1, etabSource2));
        when(etabSourceMapper.fromEtabSource(any(EtabSource.class))).thenReturn(new EtabSourceResponseDto());

        List<EtabSourceResponseDto> result = etabSourceServiceImpl.findAllEtabSour();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(etabSourceRepository, times(1)).findAll();
        verify(etabSourceMapper, times(2)).fromEtabSource(any(EtabSource.class));
    }
}