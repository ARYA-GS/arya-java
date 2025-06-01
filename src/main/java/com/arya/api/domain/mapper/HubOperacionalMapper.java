package com.arya.api.domain.mapper;

import com.arya.api.adapter.http.dto.request.HubOperacionalCadastroRequest;
import com.arya.api.adapter.http.dto.response.HubOperacionalResposta;
import com.arya.api.domain.model.Endereco;
import com.arya.api.domain.model.HubOperacional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HubOperacionalMapper {

    @Autowired
    private EnderecoMapper enderecoMapper;


    public HubOperacional converterParaModelo(HubOperacionalCadastroRequest request, Endereco endereco) {
        return HubOperacional.builder()
                .nome(request.getNome())
                .status(request.getStatus())
                .endereco(endereco)
                .build();
    }

    public HubOperacionalResposta converterParaResposta(HubOperacional hub) {
        return HubOperacionalResposta.builder()
                .idHub(hub.getIdHub())
                .nome(hub.getNome())
                .status(hub.getStatus())
                .endereco(enderecoMapper.converterParaResposta(hub.getEndereco()))
                .build();
    }
}
