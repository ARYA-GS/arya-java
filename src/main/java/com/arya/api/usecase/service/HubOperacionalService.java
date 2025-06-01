package com.arya.api.usecase.service;

import com.arya.api.adapter.http.dto.request.HubOperacionalCadastroRequest;
import com.arya.api.adapter.http.dto.response.HubOperacionalResposta;

import java.util.List;

public interface HubOperacionalService {

    HubOperacionalResposta salvar(HubOperacionalCadastroRequest request);

    HubOperacionalResposta buscarPorId(String id);

    List<HubOperacionalResposta> listarTodos();

    HubOperacionalResposta atualizar(String id, HubOperacionalCadastroRequest request);

    void deletar(String id);
}
