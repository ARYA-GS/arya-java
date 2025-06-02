package com.arya.api.adapter.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DroneModel {
    private String modelo;
    private Integer alcanceKm;
    private Double cargaKg;
    private List<String> funcoes;
}
