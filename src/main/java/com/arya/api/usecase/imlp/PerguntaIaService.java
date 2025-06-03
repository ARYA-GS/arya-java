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
                Com base nas ocorrências listadas abaixo, responda de forma clara, precisa e em linguagem acessível à seguinte pergunta enviada por um cidadão:
                
                Pergunta: %s
                
                Além disso, se a pergunta estiver relacionada a calamidades (como enchentes, deslizamentos ou incêndios), inclua:
                - Dicas práticas de primeiros socorros
                - Recomendações preventivas específicas para o tipo de situação mencionada
                
                Use as ocorrências abaixo como base de contexto para responder:
                
                %s
                """.formatted(request.getPergunta(), contexto);

        String resposta = chatClient.prompt(prompt).call().content();

        return new RespostaNaturalIA(resposta);
    }
}
