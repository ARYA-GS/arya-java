package com.arya.api.usecase.imlp;


import com.arya.api.adapter.http.client.OpenWeatherClient;
import com.arya.api.adapter.http.dto.request.OpenWeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpenWeatherServiceTest {

    @InjectMocks
    private OpenWeatherService openWeatherService;

    @Mock
    private OpenWeatherClient openWeatherClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        openWeatherService = new OpenWeatherService(openWeatherClient);
        ReflectionTestUtils.setField(openWeatherService, "apiKey", "fake-api-key");
    }


    @Test
    void deveRetornarClimaParaCidade() {
        OpenWeatherResponse respostaEsperada = new OpenWeatherResponse();
        when(openWeatherClient.getClimaAtual("São Paulo,BR", "fake-api-key", "pt_br", "metric"))
                .thenReturn(respostaEsperada);

        OpenWeatherResponse resposta = openWeatherService.buscarClima("São Paulo");

        assertNotNull(resposta);
        assertEquals(respostaEsperada, resposta);
        verify(openWeatherClient).getClimaAtual("São Paulo,BR", "fake-api-key", "pt_br", "metric");
    }

    @Test
    void deveRetornarNullQuandoChamadaFalhar() {
        when(openWeatherClient.getClimaAtual(any(), any(), any(), any())).thenThrow(RuntimeException.class);

        OpenWeatherResponse resposta = openWeatherService.buscarClima("Cidade Invalida");

        assertNull(resposta);
    }
}
