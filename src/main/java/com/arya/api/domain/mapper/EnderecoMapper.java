package com.arya.api.domain.mapper;

import com.arya.api.domain.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public Endereco converterParaModelo(EnderecoCadastroRequest request) {
        return Endereco.builder()
                .bairro(request.getBairro())
                .cidade(request.getCidade())
                .estado(request.getEstado())
                .pais(request.getPais())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
    }

    public EnderecoResposta converterParaResposta(Endereco endereco) {
        EnderecoResposta resposta = new EnderecoResposta();
        resposta.setEnderecoId(endereco.getIdEndereco());
        resposta.setBairro(endereco.getBairro());
        resposta.setCidade(endereco.getCidade());
        resposta.setEstado(endereco.getEstado());
        resposta.setPais(endereco.getPais());
        resposta.setLatitude(endereco.getLatitude());
        resposta.setLongitude(endereco.getLongitude());
        return resposta;
    }
}
