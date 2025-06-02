package com.arya.api.adapter.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumoOcorrenciaResposta {

    private String idOcorrencia;

    private String resumo;
}

