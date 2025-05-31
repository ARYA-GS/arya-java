package com.arya.api.adapter.http.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioResposta {

    private String usuarioId;
    private String nome;
    private String email;
    private LocalDate dataNasc;
}
