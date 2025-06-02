package com.arya.api.adapter.http.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DroneResposta {


    private String idDrone;

    private String modelo;

    private Integer alcanceKm;

    private Double cargaKg;

    private List<String> funcoes;
}

