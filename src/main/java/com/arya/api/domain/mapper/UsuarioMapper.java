package com.arya.api.domain.mapper;

import com.arya.api.adapter.http.dto.request.UsuarioCadastroRequest;
import com.arya.api.adapter.http.dto.request.UsuarioTrocarSenhaRequest;
import com.arya.api.adapter.http.dto.response.UsuarioResposta;
import com.arya.api.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    @Autowired
    private UsuarioValidator usuarioValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void trocarSenha(Usuario usuarioExistente, UsuarioTrocarSenhaRequest request) {
        String novaSenhaValida = usuarioValidator.validarETrocarSenha(
                usuarioExistente.getSenha(),
                request.getSenhaAtual(),
                request.getNovaSenha()
        );

        usuarioExistente.setSenha(novaSenhaValida);
    }

    public Usuario converterParaModelo(UsuarioCadastroRequest request) {
        return Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .dataNasc(request.getDataNasc())
                .build();
    }

    public UsuarioResposta converterParaResposta(Usuario usuario) {
        UsuarioResposta resposta = new UsuarioResposta();
        resposta.setUsuarioId(usuario.getUsuarioId());
        resposta.setNome(usuario.getNome());
        resposta.setEmail(usuario.getEmail());
        resposta.setDataNasc(usuario.getDataNasc());
        return resposta;
    }

    public void atualizarSenha(Usuario usuarioExistente, UsuarioTrocarSenhaRequest request) {
        String novaSenhaCriptografada = passwordEncoder.encode(request.getNovaSenha());
        usuarioExistente.setSenha(novaSenhaCriptografada);
    }
}
