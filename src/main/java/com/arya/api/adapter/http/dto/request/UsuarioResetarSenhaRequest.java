package com.arya.api.adapter.http.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioResetarSenhaRequest {
    @NotBlank @Email
    private String email;

    @NotBlank
    private String novaSenha;
}
