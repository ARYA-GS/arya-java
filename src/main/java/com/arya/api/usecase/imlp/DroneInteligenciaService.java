package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.DroneModel;
import com.arya.api.adapter.http.dto.response.SugestaoDroneResposta;
import com.arya.api.domain.model.Ocorrencia;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneInteligenciaService {

    private final ChatClient chatClient;

    public SugestaoDroneResposta sugerirDroneIdeal(Ocorrencia ocorrencia, List<DroneModel> drones) {
        String promptBase = """
            Uma nova ocorrência foi registrada com os seguintes dados:
            - Tipo: {tipo}
            - Severidade: {severidade}
            - Local: {local}
            
            Estes são os drones disponíveis:
            {drones}
            
            Com base nessas informações, indique qual drone é o mais adequado para atender essa ocorrência.
            Escolha apenas um drone e justifique a resposta de forma objetiva.
        """;

        String prompt = promptBase
                .replace("{tipo}", ocorrencia.getTipoOcorrencia())
                .replace("{severidade}", String.valueOf(ocorrencia.getNivelSeveridade()))
                .replace("{local}", ocorrencia.getEndereco().toString())
                .replace("{drones}", new Gson().toJson(drones));

        String resposta = chatClient.prompt(prompt).call().content();

        return new SugestaoDroneResposta(ocorrencia.getIdOcorrencia(), resposta);
    }
}

