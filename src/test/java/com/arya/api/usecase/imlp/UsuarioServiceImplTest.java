package com.arya.api.usecase.imlp;

import com.arya.api.adapter.repository.UsuarioRepository;
import com.arya.api.domain.exception.EmailJaCadastradoException;
import com.arya.api.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UsuarioServiceImplTest {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll();
    }

    @Test
    void deveSalvarUsuarioQuandoEmailNaoExiste() {
        Usuario usuario = Usuario.builder()
                .nome("Usuario")
                .email("usuario@example.com")
                .senha("123456")
                .build();

        Usuario salvo = usuarioService.salvar(usuario);

        assertNotNull(salvo.getUsuarioId());
        assertEquals(1, usuarioRepository.count());
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaCadastrado() {
        Usuario existente = Usuario.builder()
                .nome("Existente")
                .email("existente@example.com")
                .senha("123")
                .build();
        usuarioRepository.save(existente);

        Usuario novo = Usuario.builder()
                .nome("Novo")
                .email("existente@example.com")
                .senha("abc")
                .build();

        assertThrows(EmailJaCadastradoException.class, () -> usuarioService.salvar(novo));
    }
}
