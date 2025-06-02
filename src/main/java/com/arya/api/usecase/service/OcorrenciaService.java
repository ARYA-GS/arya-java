package com.arya.api.usecase.service;

import com.arya.api.adapter.http.dto.request.OcorrenciaCadastroRequest;
import com.arya.api.adapter.http.dto.response.OcorrenciaResposta;
import com.arya.api.adapter.http.dto.response.ResumoOcorrenciaResposta;

import java.util.List;

public interface OcorrenciaService {

    OcorrenciaResposta salvar(OcorrenciaCadastroRequest request);

    List<OcorrenciaResposta> listarTodos();

    OcorrenciaResposta buscarPorId(String id);

    void deletar(String id);

    ResumoOcorrenciaResposta gerarResumoOcorrencia(String ocorrenciaId);
}
