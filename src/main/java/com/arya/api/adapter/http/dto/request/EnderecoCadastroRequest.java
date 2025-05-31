package com.arya.api.adapter.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnderecoCadastroRequest {

    private String bairro;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    @NotBlank
    private String pais;

}
