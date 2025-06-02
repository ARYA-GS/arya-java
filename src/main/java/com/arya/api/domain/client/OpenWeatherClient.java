package com.arya.api.domain.client;

import com.arya.api.adapter.http.dto.request.OpenWeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openWeatherClient", url = "https://api.openweathermap.org/data/2.5")
public interface OpenWeatherClient {

    @GetMapping("/weather")
    OpenWeatherResponse getClimaAtual(
            @RequestParam("q") String cidade,
            @RequestParam("appid") String apiKey,
            @RequestParam("lang") String lang,
            @RequestParam("units") String units
    );
}

