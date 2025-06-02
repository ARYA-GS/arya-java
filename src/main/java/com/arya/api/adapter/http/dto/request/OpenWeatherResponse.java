package com.arya.api.adapter.http.dto.request;

import com.arya.api.domain.model.Main;
import com.arya.api.domain.model.Weather;
import com.arya.api.domain.model.Wind;
import lombok.Data;

import java.util.List;

@Data
public class OpenWeatherResponse {
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private String name;
}
