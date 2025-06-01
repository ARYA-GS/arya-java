package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.DroneCadastroRequest;
import com.arya.api.adapter.http.dto.response.DroneResposta;
import com.arya.api.adapter.repository.DroneRepository;
import com.arya.api.adapter.repository.EspecificacaoRepository;
import com.arya.api.adapter.repository.HubOperacionalRepository;
import com.arya.api.domain.mapper.DroneMapper;
import com.arya.api.domain.mapper.EspecificacaoMapper;
import com.arya.api.domain.model.Drone;
import com.arya.api.domain.model.Especificacao;
import com.arya.api.domain.model.HubOperacional;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DroneServiceImplTest {

    @Mock
    private DroneRepository droneRepository;
    @Mock
    private EspecificacaoRepository especificacaoRepository;
    @Mock
    private HubOperacionalRepository hubRepository;
    @Mock
    private DroneMapper droneMapper;
    @Mock
    private EspecificacaoMapper especificacaoMapper;

    @InjectMocks
    private DroneServiceImpl droneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvar_DeveRetornarDroneResposta() {
        DroneCadastroRequest request = mock(DroneCadastroRequest.class);
        HubOperacional hub = new HubOperacional();
        Especificacao especificacao = new Especificacao();
        Drone drone = new Drone();
        DroneResposta resposta = new DroneResposta();

        when(request.getIdHub()).thenReturn("hub1");
        when(hubRepository.findById("hub1")).thenReturn(Optional.of(hub));
        when(especificacaoMapper.converterParaModelo(any())).thenReturn(especificacao);
        when(especificacaoRepository.save(any())).thenReturn(especificacao);
        when(droneMapper.converterParaModelo(request, hub, especificacao)).thenReturn(drone);
        when(droneRepository.save(drone)).thenReturn(drone);
        when(droneMapper.converterParaResposta(drone)).thenReturn(resposta);

        DroneResposta result = droneService.salvar(request);

        assertEquals(resposta, result);
    }

    @Test
    void listarTodos_DeveRetornarListaDroneResposta() {
        Drone drone = new Drone();
        DroneResposta resposta = new DroneResposta();

        when(droneRepository.findAll()).thenReturn(Arrays.asList(drone));
        when(droneMapper.converterParaResposta(drone)).thenReturn(resposta);

        var result = droneService.listarTodos();

        assertEquals(1, result.size());
        assertEquals(resposta, result.get(0));
    }

    @Test
    void buscarPorId_QuandoEncontrado_DeveRetornarDroneResposta() {
        Drone drone = new Drone();
        DroneResposta resposta = new DroneResposta();

        when(droneRepository.findById("1")).thenReturn(Optional.of(drone));
        when(droneMapper.converterParaResposta(drone)).thenReturn(resposta);

        DroneResposta result = droneService.buscarPorId("1");

        assertEquals(resposta, result);
    }

    @Test
    void buscarPorId_QuandoNaoEncontrado_DeveLancarExcecao() {
        when(droneRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> droneService.buscarPorId("1"));
    }

    @Test
    void atualizar_DeveRetornarDroneRespostaAtualizado() {
        DroneCadastroRequest request = mock(DroneCadastroRequest.class);
        Drone existente = new Drone();
        HubOperacional hub = new HubOperacional();
        Especificacao novaEspecificacao = new Especificacao();
        DroneResposta resposta = new DroneResposta();

        when(droneRepository.findById("1")).thenReturn(Optional.of(existente));
        when(request.getIdHub()).thenReturn("hub1");
        when(hubRepository.findById("hub1")).thenReturn(Optional.of(hub));
        when(especificacaoMapper.converterParaModelo(any())).thenReturn(novaEspecificacao);
        when(especificacaoRepository.save(any())).thenReturn(novaEspecificacao);
        when(droneRepository.save(existente)).thenReturn(existente);
        when(droneMapper.converterParaResposta(existente)).thenReturn(resposta);

        DroneResposta result = droneService.atualizar("1", request);

        assertEquals(resposta, result);
    }

    @Test
    void deletar_DeveRemoverDrone() {
        Drone drone = new Drone();
        when(droneRepository.findById("1")).thenReturn(Optional.of(drone));

        droneService.deletar("1");

        verify(droneRepository).delete(drone);
    }

    @Test
    void deletar_QuandoNaoEncontrado_DeveLancarExcecao() {
        when(droneRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> droneService.deletar("1"));
    }
}