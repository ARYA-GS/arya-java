package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.PerguntaRequest;
import com.arya.api.adapter.http.dto.response.RespostaNaturalIA;
import com.arya.api.adapter.repository.OcorrenciaRepository;
import com.arya.api.domain.model.Ocorrencia;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerguntaIaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private ChatClient chatClient;

    public RespostaNaturalIA responder(PerguntaRequest request) {
        List<Ocorrencia> todas = ocorrenciaRepository.findAll();

        StringBuilder contexto = new StringBuilder("Ocorrências registradas:\n");
        for (Ocorrencia o : todas) {
            contexto.append("- Tipo: ").append(o.getTipoOcorrencia())
                    .append(" | Cidade: ").append(o.getEndereco().getCidade())
                    .append(" | Bairro: ").append(o.getEndereco().getBairro())
                    .append(" | Data: ").append(o.getDataOcorrencia()).append("\n");
        }

        String prompt = """
                Com base nas ocorrências listadas abaixo, responda de forma clara, objetiva e voltada para a gestão dos incidentes à seguinte pergunta:
                
                Pergunta: %s
                
                Use os dados abaixo como base para identificar padrões, estatísticas e apoiar a tomada de decisão (ex: quantidade de incidentes, regiões mais afetadas, tipos mais comuns).
                
                Evite explicações longas ou genéricas. Foque em dados diretos e úteis para análise operacional.
                
                %s
                """.formatted(request.getPergunta(), contexto);

        String resposta = chatClient.prompt(prompt).call().content();

        return new RespostaNaturalIA(resposta);
    }
}
