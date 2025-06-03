package com.arya.api.usecase.service;

import com.arya.api.adapter.http.dto.request.UsuarioResetarSenhaRequest;
import com.arya.api.adapter.http.dto.request.UsuarioTrocarSenhaRequest;
import com.arya.api.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario salvar(Usuario usuario);

    List<Usuario> listarTodos();

    Optional<Usuario> buscarPorId(String usuarioId);

    Optional<Usuario> buscarPorEmail(String email);

    Usuario trocarSenha(String id, UsuarioTrocarSenhaRequest request);

    void deletar(String usuarioId);

    String validarLogin(String email, String senhaDigitada);

    void resetarSenhaPorEmail(UsuarioResetarSenhaRequest request);


}
