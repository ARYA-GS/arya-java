package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.OpenWeatherResponse;
import com.arya.api.adapter.http.dto.response.PrevisaoRiscoResposta;
import com.arya.api.adapter.repository.OcorrenciaRepository;
import com.arya.api.domain.model.Ocorrencia;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PrevisaoRiscoService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final OpenWeatherService openWeatherService;
    private final ChatClient chatClient;

    public PrevisaoRiscoResposta preverRisco() {
        List<Ocorrencia> todas = ocorrenciaRepository.findAll();

        StringBuilder historico = new StringBuilder("Histórico de ocorrências:\n");
        Set<String> cidades = new HashSet<>();

        for (Ocorrencia o : todas) {
            String cidade = o.getEndereco().getCidade();
            cidades.add(cidade);
            historico.append("- Tipo: ").append(o.getTipoOcorrencia())
                    .append(" | Cidade: ").append(cidade)
                    .append(" | Bairro: ").append(o.getEndereco().getBairro())
                    .append(" | Data: ").append(o.getDataOcorrencia()).append("\n");
        }

        StringBuilder climaAtual = new StringBuilder("Clima atual:\n");
        for (String cidade : cidades) {
            OpenWeatherResponse clima = openWeatherService.buscarClima(cidade);
            if (clima != null && clima.getMain() != null && !clima.getWeather().isEmpty()) {
                climaAtual.append("- ").append(cidade)
                        .append(": ").append(clima.getWeather().get(0).getDescription())
                        .append(", ").append(clima.getMain().getTemp()).append("°C, ")
                        .append(clima.getMain().getHumidity()).append("% umidade\n");
            }
        }

        String prompt = """
                Com base no histórico de ocorrências e nas condições climáticas atuais, identifique:
                
                1. Regiões com maior risco de uma nova calamidade.
                2. O tipo de calamidade mais provável em cada local.
                3. Ações preventivas recomendadas para cada região (ex: alerta preventivo, evacuação, envio de drone, monitoramento via sensores).
                
                Se não houver informações suficientes para prever, informe isso com clareza.
                
                %s
                
                %s
                """.formatted(historico, climaAtual);

        String resposta = chatClient.prompt(prompt).call().content();

        return new PrevisaoRiscoResposta(resposta);
    }
}
