package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.DroneCadastroRequest;
import com.arya.api.adapter.http.dto.response.DroneResposta;
import com.arya.api.infra.security.TokenService;
import com.arya.api.usecase.service.DroneService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DroneController.class)
@AutoConfigureMockMvc(addFilters = false)
class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneService droneService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private com.arya.api.adapter.repository.UsuarioRepository usuarioRepository;

    @Test
    void deveRetornarListaVazia() throws Exception {
        Mockito.when(droneService.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/drones"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void deveCadastrarDroneComSucesso() throws Exception {
        DroneResposta resposta = new DroneResposta("1", "Modelo X", 10, 5.0, List.of("Vistoria"));
        Mockito.when(droneService.salvar(any())).thenReturn(resposta);

        String json = """
                {
                  "modelo": "Modelo X",
                  "alcanceKm": 10,
                  "cargaKg": 5.0,
                  "funcoes": ["Vistoria"]
                }
                """;

        mockMvc.perform(post("/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.modelo").value("Modelo X"));
    }

    @Test
    void deveListarTodosOsDrones() throws Exception {
        List<DroneResposta> drones = List.of(new DroneResposta("1", "Drone A", 15, 3.5, List.of("Monitoramento")));
        Mockito.when(droneService.listarTodos()).thenReturn(drones);

        mockMvc.perform(get("/drones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").value("Drone A"));
    }

    @Test
    void deveBuscarDronePorId() throws Exception {
        DroneResposta drone = new DroneResposta("1", "Drone A", 15, 3.5, List.of("Entrega"));
        Mockito.when(droneService.buscarPorId("1")).thenReturn(drone);

        mockMvc.perform(get("/drones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Drone A"));
    }

    @Test
    void deveAtualizarDrone() throws Exception {
        DroneResposta atualizado = new DroneResposta("1", "Atualizado", 20, 6.0, List.of("Resgate"));
        Mockito.when(droneService.atualizar(eq("1"), any())).thenReturn(atualizado);

        String json = """
                {
                  "modelo": "Atualizado",
                  "alcanceKm": 20,
                  "cargaKg": 6.0,
                  "funcoes": ["Resgate"]
                }
                """;

        mockMvc.perform(put("/drones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Atualizado"));
    }

    @Test
    void deveDeletarDrone() throws Exception {
        mockMvc.perform(delete("/drones/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(droneService).deletar("1");
    }

    @Test
    void deveRetornar404QuandoDroneNaoEncontrado() throws Exception {
        Mockito.when(droneService.buscarPorId("999"))
                .thenThrow(new EntityNotFoundException("Drone não encontrado"));

        mockMvc.perform(get("/drones/999"))
                .andExpect(status().isNotFound());
    }
}
