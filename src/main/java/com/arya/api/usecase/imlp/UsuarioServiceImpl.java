package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.UsuarioTrocarSenhaRequest;
import com.arya.api.adapter.repository.UsuarioRepository;
import com.arya.api.domain.exception.EmailJaCadastradoException;
import com.arya.api.domain.mapper.UsuarioMapper;
import com.arya.api.domain.model.Usuario;
import com.arya.api.infra.security.SecurityConfiguration;
import com.arya.api.infra.security.TokenService;
import com.arya.api.usecase.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SecurityConfiguration securityConfig;

    @Override
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.findByEmail(usuario.getEmail())
                .ifPresent(u -> {
                    throw new EmailJaCadastradoException(usuario.getEmail());
                });

        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario trocarSenha(String id, UsuarioTrocarSenhaRequest request) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        usuarioMapper.atualizarSenha(usuarioExistente, request);

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void deletar(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

//    @Override
//    public String validarLogin(String email, String senhaDigitada) {
//        Usuario usuario = usuarioRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
//
//    if (securityConfig.passwordEncoder().matches(senhaDigitada, usuario.getSenha())) {
//        throw new BadCredentialsException("Senha incorreta");
//    }
//
//
//        return tokenService.gerarToken(usuario);
//    }
}
