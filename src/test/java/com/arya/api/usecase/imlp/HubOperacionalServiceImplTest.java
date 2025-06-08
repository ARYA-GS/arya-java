package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.EnderecoCadastroRequest;
import com.arya.api.adapter.http.dto.request.HubOperacionalCadastroRequest;
import com.arya.api.adapter.http.dto.response.EnderecoResposta;
import com.arya.api.adapter.http.dto.response.HubOperacionalResposta;
import com.arya.api.adapter.repository.EnderecoRepository;
import com.arya.api.adapter.repository.HubOperacionalRepository;
import com.arya.api.domain.mapper.EnderecoMapper;
import com.arya.api.domain.mapper.HubOperacionalMapper;
import com.arya.api.domain.model.Endereco;
import com.arya.api.domain.model.HubOperacional;
import com.arya.api.infra.messaging.HubProducer;
import com.arya.api.usecase.imlp.GeocodingService;
import com.arya.api.usecase.imlp.HubOperacionalServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HubOperacionalServiceImplTest {

    @InjectMocks
    private HubOperacionalServiceImpl service;

    @Mock
    private HubOperacionalRepository hubRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private EnderecoMapper enderecoMapper;

    @Mock
    private HubOperacionalMapper hubMapper;

    @Mock
    private GeocodingService geocodingService;

    @Mock
    private HubProducer hubProducer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarNovoHubComSucesso() {
        HubOperacionalCadastroRequest request = new HubOperacionalCadastroRequest();
        request.setNome("Centro Norte");
        request.setStatus("ATIVO");

        EnderecoCadastroRequest enderecoRequest = new EnderecoCadastroRequest();
        enderecoRequest.setBairro("Centro");
        enderecoRequest.setCidade("São Paulo");
        enderecoRequest.setEstado("SP");
        enderecoRequest.setPais("Brasil");
        request.setEndereco(enderecoRequest);

        Endereco endereco = new Endereco();
        HubOperacional hub = new HubOperacional();
        HubOperacionalResposta resposta = mock(HubOperacionalResposta.class);

        when(enderecoMapper.converterParaModelo(enderecoRequest)).thenReturn(endereco);
        when(geocodingService.obterCoordenadas(anyString())).thenReturn(Optional.of(new double[]{-23.5, -46.6}));
        when(hubRepository.existsByEndereco_LatitudeAndEndereco_Longitude(-23.5, -46.6)).thenReturn(false);
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        when(hubMapper.converterParaModelo(request, endereco)).thenReturn(hub);
        when(hubRepository.save(hub)).thenReturn(hub);
        when(hubMapper.converterParaResposta(hub)).thenReturn(resposta);

        HubOperacionalResposta resultado = service.salvar(request);

        assertNotNull(resultado);
        verify(hubProducer, times(1)).send(hub);
    }


    @Test
    void deveLancarExcecaoSeHubNoMesmoPonto() {
        HubOperacionalCadastroRequest request = new HubOperacionalCadastroRequest();
        EnderecoCadastroRequest enderecoRequest = new EnderecoCadastroRequest();
        enderecoRequest.setBairro("Centro");
        enderecoRequest.setCidade("São Paulo");
        enderecoRequest.setEstado("SP");
        enderecoRequest.setPais("Brasil");
        request.setEndereco(enderecoRequest);

        Endereco endereco = new Endereco();

        when(enderecoMapper.converterParaModelo(any())).thenReturn(endereco);
        when(geocodingService.obterCoordenadas(any())).thenReturn(Optional.of(new double[]{-23.5, -46.6}));
        when(hubRepository.existsByEndereco_LatitudeAndEndereco_Longitude(-23.5, -46.6)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.salvar(request));
    }

    @Test
    void deveBuscarHubPorId() {
        HubOperacional hub = new HubOperacional();
        HubOperacionalResposta resposta = mock(HubOperacionalResposta.class);

        when(hubRepository.findById("123")).thenReturn(Optional.of(hub));
        when(hubMapper.converterParaResposta(hub)).thenReturn(resposta);

        HubOperacionalResposta resultado = service.buscarPorId("123");

        assertNotNull(resultado);
    }

    @Test
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(hubRepository.findById("999")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.buscarPorId("999"));
    }

    @Test
    void deveListarTodosHubs() {
        List<HubOperacional> hubs = List.of(new HubOperacional(), new HubOperacional());
        when(hubRepository.findAll()).thenReturn(hubs);
        when(hubMapper.converterParaResposta(any())).thenReturn(mock(HubOperacionalResposta.class));

        List<HubOperacionalResposta> resultado = service.listarTodos();

        assertEquals(2, resultado.size());
    }


    @Test
    void deveAtualizarHubComSucesso() {
        HubOperacional existente = new HubOperacional();
        Endereco novoEndereco = new Endereco();
        HubOperacionalCadastroRequest request = new HubOperacionalCadastroRequest();
        EnderecoCadastroRequest enderecoRequest = new EnderecoCadastroRequest();
        request.setEndereco(enderecoRequest);
        request.setNome("Atualizado");
        request.setStatus("ATIVO");

        when(hubRepository.findById("abc")).thenReturn(Optional.of(existente));
        when(enderecoMapper.converterParaModelo(any())).thenReturn(novoEndereco);
        when(geocodingService.obterCoordenadas(any())).thenReturn(Optional.of(new double[]{1.1, 2.2}));
        when(enderecoRepository.save(novoEndereco)).thenReturn(novoEndereco);
        when(hubRepository.save(any())).thenReturn(existente);
        when(hubMapper.converterParaResposta(any())).thenReturn(mock(HubOperacionalResposta.class));

        HubOperacionalResposta atualizado = service.atualizar("abc", request);

        assertNotNull(atualizado);
        verify(hubRepository).save(existente);
    }

    @Test
    void deveDeletarHubComSucesso() {
        HubOperacional hub = new HubOperacional();
        when(hubRepository.findById("id")).thenReturn(Optional.of(hub));

        service.deletar("id");

        verify(hubRepository, times(1)).delete(hub);
    }

    @Test
    void deveLancarExcecaoAoDeletarIdInexistente() {
        when(hubRepository.findById("notfound")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deletar("notfound"));
    }
}
