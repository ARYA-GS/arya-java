package com.arya.api.adapter.http.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OcorrenciaCadastroRequest {
    @NotBlank
    private String tipoOcorrencia;

    @NotNull
    private Integer nivelSeveridade;

    @NotNull
    private OffsetDateTime dataOcorrencia;

    private String descricao;

    @NotBlank
    private String idUsuario;

    @Valid
    @NotNull
    private EnderecoCadastroRequest endereco;
}