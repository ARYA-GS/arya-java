package com.arya.api.usecase.service;

import com.arya.api.adapter.http.dto.request.DroneCadastroRequest;
import com.arya.api.adapter.http.dto.response.DroneResposta;

import java.util.List;

public interface DroneService {

    DroneResposta salvar(DroneCadastroRequest request);

    List<DroneResposta> listarTodos();

    DroneResposta buscarPorId(String id);

    DroneResposta atualizar(String id, DroneCadastroRequest request);

    void deletar(String id);
}
