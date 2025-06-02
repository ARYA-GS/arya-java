package com.arya.api.adapter.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DroneCadastroRequest {

    @NotBlank
    private String idHub;

    @NotBlank
    private String modelo;

    @NotNull
    private Integer alcanceKm;

    @NotNull
    private Double cargaKg;

    @NotNull
    private List<@NotBlank String> funcoes;
}