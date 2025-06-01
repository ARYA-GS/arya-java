package com.arya.api.adapter.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EspecificacaoCadastroRequest {

    @NotBlank
    private String fabricante;

    @NotNull
    private Integer autonomiaMinutos;

    @NotBlank
    private String tipoDrone;

    @NotBlank
    private String modelo;
}