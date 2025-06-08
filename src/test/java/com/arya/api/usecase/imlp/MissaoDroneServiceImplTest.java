package com.arya.api.usecase.imlp;


import com.arya.api.adapter.repository.MissaoDroneRepository;
import com.arya.api.domain.model.Drone;
import com.arya.api.domain.model.MissaoDrone;
import com.arya.api.domain.model.Ocorrencia;
import com.arya.api.usecase.imlp.MissaoDroneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MissaoDroneServiceImplTest {

    @InjectMocks
    private MissaoDroneServiceImpl service;

    @Mock
    private MissaoDroneRepository missaoDroneRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRegistrarMissaoComSucesso() {
        // Arrange
        Drone drone = new Drone();
        Ocorrencia ocorrencia = new Ocorrencia();

        MissaoDrone missaoSalva = MissaoDrone.builder()
                .drone(drone)
                .ocorrencia(ocorrencia)
                .dataInicio(OffsetDateTime.now())
                .status("DESPACHADO")
                .build();

        when(missaoDroneRepository.save(any(MissaoDrone.class))).thenReturn(missaoSalva);

        // Act
        MissaoDrone resultado = service.registrarMissao(drone, ocorrencia);

        // Assert
        assertNotNull(resultado);
        assertEquals("DESPACHADO", resultado.getStatus());
        assertEquals(drone, resultado.getDrone());
        assertEquals(ocorrencia, resultado.getOcorrencia());
        assertNotNull(resultado.getDataInicio());
        verify(missaoDroneRepository).save(any(MissaoDrone.class));
    }
}
