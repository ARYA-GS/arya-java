package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.EnderecoCadastroRequest;
import com.arya.api.adapter.http.dto.request.OcorrenciaCadastroRequest;
import com.arya.api.adapter.http.dto.response.OcorrenciaResposta;
import com.arya.api.adapter.repository.AreaOperacaoRepository;
import com.arya.api.adapter.repository.OcorrenciaRepository;
import com.arya.api.adapter.repository.UsuarioRepository;
import com.arya.api.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class OcorrenciaServiceImplTest {

    @Autowired
    private OcorrenciaServiceImpl ocorrenciaService;

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AreaOperacaoRepository areaOperacaoRepository;

    @MockBean
    private GeocodingService geocodingService;

    @BeforeEach
    void setup() {
        ocorrenciaRepository.deleteAll();
        areaOperacaoRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    void deveSalvarOcorrenciaComSucesso() {
        Usuario usuario = Usuario.builder()
                .nome("Usuario")
                .email("teste@ex.com")
                .senha("123")
                .build();
        usuario = usuarioRepository.save(usuario);

        when(geocodingService.obterCoordenadas(anyString()))
                .thenReturn(Optional.of(new double[]{10.0, 20.0}));

        EnderecoCadastroRequest endereco = new EnderecoCadastroRequest();
        endereco.setBairro("Centro");
        endereco.setCidade("Cidade");
        endereco.setEstado("ST");
        endereco.setPais("BR");

        OcorrenciaCadastroRequest request = new OcorrenciaCadastroRequest();
        request.setTipoOcorrencia("Fogo");
        request.setNivelSeveridade(1);
        request.setDataOcorrencia(OffsetDateTime.now());
        request.setDescricao("desc");
        request.setIdUsuario(usuario.getUsuarioId());
        request.setEndereco(endereco);

        OcorrenciaResposta resposta = ocorrenciaService.salvar(request);

        assertNotNull(resposta.getIdOcorrencia());
        assertEquals(1, ocorrenciaRepository.count());
    }
}
