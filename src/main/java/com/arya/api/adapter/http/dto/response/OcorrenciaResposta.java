package com.arya.api.adapter.http.dto.response;

import com.arya.api.adapter.http.dto.request.EnderecoCadastroRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcorrenciaResposta {

    private String idOcorrencia;

    private String tipoOcorrencia;

    private Integer nivelSeveridade;

    private OffsetDateTime dataOcorrencia;

    private String descricao;

    private String nomeUsuario;

    private EnderecoResposta endereco;

    private Double latitudeCentralArea;

    private Double longitudeCentralArea;
}