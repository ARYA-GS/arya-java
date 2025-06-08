package com.arya.api.usecase.imlp;


import com.arya.api.adapter.http.dto.request.UsuarioResetarSenhaRequest;
import com.arya.api.adapter.http.dto.request.UsuarioTrocarSenhaRequest;
import com.arya.api.adapter.repository.UsuarioRepository;
import com.arya.api.domain.exception.EmailJaCadastradoException;
import com.arya.api.domain.mapper.UsuarioMapper;
import com.arya.api.domain.model.Usuario;
import com.arya.api.infra.security.SecurityConfiguration;
import com.arya.api.infra.security.TokenService;
import com.arya.api.usecase.imlp.UsuarioServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl service;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private TokenService tokenService;

    @Mock
    private SecurityConfiguration securityConfiguration;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(securityConfiguration.passwordEncoder()).thenReturn(passwordEncoder);
    }

    @Test
    void deveSalvarUsuarioComSenhaCriptografada() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("123456");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario salvo = service.salvar(usuario);

        assertNotNull(salvo);
        assertEquals("senhaCriptografada", usuario.getSenha());
    }

    @Test
    void deveLancarExcecaoSeEmailJaCadastrado() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(new Usuario()));

        assertThrows(EmailJaCadastradoException.class, () -> service.salvar(usuario));
    }

    @Test
    void deveTrocarSenhaComSucesso() {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId("1");

        UsuarioTrocarSenhaRequest request = new UsuarioTrocarSenhaRequest();

        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario atualizado = service.trocarSenha("1", request);

        assertNotNull(atualizado);
        verify(usuarioMapper).atualizarSenha(usuario, request);
    }

    @Test
    void deveResetarSenhaPorEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");

        UsuarioResetarSenhaRequest request = new UsuarioResetarSenhaRequest();
        request.setEmail("teste@email.com");
        request.setNovaSenha("novaSenha");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        service.resetarSenhaPorEmail(request);

        assertEquals("novaSenha", usuario.getSenha());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void deveValidarLoginComSucesso() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("senhaCriptografada");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("123456", "senhaCriptografada")).thenReturn(true);
        when(tokenService.gerarToken(usuario)).thenReturn("token.jwt");

        String token = service.validarLogin("teste@email.com", "123456");

        assertEquals("token.jwt", token);
    }

    @Test
    void deveLancarExcecaoSeSenhaIncorreta() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("senhaCriptografada");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", "senhaCriptografada")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> service.validarLogin("teste@email.com", "senhaErrada"));
    }

    @Test
    void deveDeletarUsuarioExistente() {
        when(usuarioRepository.existsById("1")).thenReturn(true);

        service.deletar("1");

        verify(usuarioRepository).deleteById("1");
    }

    @Test
    void deveLancarExcecaoSeUsuarioNaoEncontradoAoDeletar() {
        when(usuarioRepository.existsById("1")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.deletar("1"));
    }
}
