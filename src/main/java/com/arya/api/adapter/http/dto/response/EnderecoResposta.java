package com.arya.api.adapter.http.dto.response;

import lombok.Data;

@Data
public class EnderecoResposta {

    private String enderecoId;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private Double latitude;
    private Double longitude;
}
