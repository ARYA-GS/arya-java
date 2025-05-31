package com.arya.api.usecase.service;

import com.arya.api.domain.model.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoService {

    Endereco salvar(Endereco endereco);

    List<Endereco> listarTodos();

    Optional<Endereco> buscarPorId(String id);

    Endereco atualizar(String id, Endereco endereco);

    void deletar(String id);
}
