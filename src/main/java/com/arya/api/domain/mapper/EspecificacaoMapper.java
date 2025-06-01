package com.arya.api.domain.mapper;

import com.arya.api.adapter.http.dto.request.EspecificacaoCadastroRequest;
import com.arya.api.domain.model.Especificacao;
import org.springframework.stereotype.Component;

@Component
public class EspecificacaoMapper {

    public Especificacao converterParaModelo(EspecificacaoCadastroRequest request) {
        return Especificacao.builder()
                .fabricante(request.getFabricante())
                .autonomiaMinutos(request.getAutonomiaMinutos())
                .tipoDrone(request.getTipoDrone())
                .modelo(request.getModelo())
                .build();
    }
}