package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.EnderecoCadastroRequest;
import com.arya.api.adapter.http.dto.request.OcorrenciaCadastroRequest;
import com.arya.api.adapter.http.dto.response.EnderecoResposta;
import com.arya.api.adapter.http.dto.response.OcorrenciaResposta;
import com.arya.api.adapter.http.dto.response.ResumoOcorrenciaResposta;
import com.arya.api.infra.security.TokenService;
import com.arya.api.usecase.service.OcorrenciaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OcorrenciaController.class)
@AutoConfigureMockMvc(addFilters = false)
class OcorrenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OcorrenciaService ocorrenciaService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private com.arya.api.adapter.repository.UsuarioRepository usuarioRepository;

    @Test
    void deveCadastrarOcorrenciaComSucesso() throws Exception {
        OcorrenciaResposta resposta = new OcorrenciaResposta(
                "1",
                "Incêndio",
                3,
                OffsetDateTime.now(),
                "Crítica",
                "Ativa",
                new EnderecoResposta(),
                -23.0,
                -46.0
        );

        Mockito.when(ocorrenciaService.salvar(any())).thenReturn(resposta);

        String json = """
            {
              "tipoOcorrencia": "Incêndio",
                        "nivelSeveridade": 3,
                        "dataOcorrencia": "2025-06-07T21:45:35.5191375-03:00",
                        "descricao": "Crítica",
                        "nomeUsuario": "Ativa",
                        "idUsuario": "123",
                        "latitudeCentralArea": -23.0,
                        "longitudeCentralArea": -46.0,
                        "endereco": {
                          "cidade": "São Paulo",
                          "estado": "SP",
                          "pais": "Brasil"
              }
            }
        """;

        mockMvc.perform(post("/ocorrencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idOcorrencia").value("1"))
                .andExpect(jsonPath("$.tipoOcorrencia").value("Incêndio"));
    }

    @Test
    void deveBuscarOcorrenciaPorId() throws Exception {
        OcorrenciaResposta resposta = new OcorrenciaResposta(
                "1",
                "Incêndio",
                3,
                OffsetDateTime.now(),
                "Crítica",
                "Ativa",
                new EnderecoResposta(),
                -23.0,
                -46.0
        );

        Mockito.when(ocorrenciaService.buscarPorId("1")).thenReturn(resposta);

        mockMvc.perform(get("/ocorrencias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoOcorrencia").value("Incêndio"));
    }

    @Test
    void deveListarTodasOcorrencias() throws Exception {
        OcorrenciaResposta resposta = new OcorrenciaResposta(
                "1",
                "Incêndio",
                3,
                OffsetDateTime.now(),
                "Crítica",
                "Ativa",
                new EnderecoResposta(),
                -23.0,
                -46.0
        );

        Mockito.when(ocorrenciaService.listarTodos()).thenReturn(List.of(resposta));

        mockMvc.perform(get("/ocorrencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idOcorrencia").value("1"));
    }

    @Test
    void deveDeletarOcorrencia() throws Exception {
        mockMvc.perform(delete("/ocorrencias/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(ocorrenciaService).deletar("1");
    }

    @Test
    void deveGerarResumoOcorrencia() throws Exception {
        ResumoOcorrenciaResposta resposta = new ResumoOcorrenciaResposta("Resumo da ocorrência", "Urgência alta");

        Mockito.when(ocorrenciaService.gerarResumoOcorrencia("1")).thenReturn(resposta);

        mockMvc.perform(get("/ocorrencias/1/resumo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOcorrencia").value("Resumo da ocorrência"))
                .andExpect(jsonPath("$.resumo").value("Urgência alta"));
    }

}
