package com.arya.api.adapter.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SugestaoDroneResposta {

    private String idOcorrencia;

    private String sugestao;
}