package com.arya.api.adapter.http.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HubOperacionalCadastroRequest {

    @NotBlank
    private String nome;

    private String status;

    @Valid
    @NotNull
    private EnderecoCadastroRequest endereco;
}