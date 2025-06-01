package com.arya.api.adapter.http.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DroneCadastroRequest {

    @NotBlank
    private String nome;

    private String status;

    private String carregamento;

    @NotBlank
    private String idHub;

    @NotNull
    @Valid
    private EspecificacaoCadastroRequest especificacao;
}