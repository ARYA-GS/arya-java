package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.OpenWeatherResponse;
import com.arya.api.adapter.http.dto.response.PrevisaoRiscoResposta;
import com.arya.api.adapter.repository.OcorrenciaRepository;
import com.arya.api.domain.model.Ocorrencia;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import com.arya.api.infra.notification.NotificationService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PrevisaoRiscoService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final OpenWeatherService openWeatherService;
    private final ChatClient chatClient;
    private final NotificationService notificationService;

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
                Com base no histórico de ocorrências e nas condições climáticas atuais, responda de forma objetiva e resumida:
                
                1. Regiões com maior risco de nova calamidade.
                2. Tipo mais provável de calamidade por região.
                3. Ação preventiva principal recomendada por região.
                
                Se não houver dados suficientes, informe isso claramente. Evite respostas longas ou detalhadas.
                
                %s
                
                %s
                """.formatted(historico, climaAtual);

        String resposta = chatClient.prompt(prompt).call().content();

        if (resposta.toLowerCase().contains("alto risco") || resposta.toLowerCase().contains("evacuação")) {
            notificationService.enviarAlerta("Alto risco de calamidade detectado");
        }

        return new PrevisaoRiscoResposta(resposta);
    }
}
