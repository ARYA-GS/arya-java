package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.OpenWeatherResponse;
import com.arya.api.adapter.http.client.OpenWeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenWeatherService {

    @Value("${openweather.api-key}")
    private String apiKey;

    private final OpenWeatherClient openWeatherClient;

    public OpenWeatherResponse buscarClima(String cidade) {
        try {
            return openWeatherClient.getClimaAtual(
                    cidade + ",BR",
                    apiKey,
                    "pt_br",
                    "metric"
            );
        } catch (Exception e) {
            return null;
        }
    }
}
