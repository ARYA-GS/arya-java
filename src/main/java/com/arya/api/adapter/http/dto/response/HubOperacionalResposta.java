package com.arya.api.adapter.http.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HubOperacionalResposta {

    private String idHub;
    private String nome;
    private String status;
    private EnderecoResposta endereco;
}
