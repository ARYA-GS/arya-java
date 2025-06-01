package com.arya.api.domain.mapper;

import com.arya.api.adapter.http.dto.request.OcorrenciaCadastroRequest;
import com.arya.api.adapter.http.dto.response.OcorrenciaResposta;
import com.arya.api.domain.model.AreaOperacao;
import com.arya.api.domain.model.Endereco;
import com.arya.api.domain.model.Ocorrencia;
import com.arya.api.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class OcorrenciaMapper {

    @Autowired
    private EnderecoMapper enderecoMapper;

    public Ocorrencia converterParaModelo(OcorrenciaCadastroRequest request, Usuario usuario, Endereco endereco, AreaOperacao areaOperacao) {
        return Ocorrencia.builder()
                .tipoOcorrencia(request.getTipoOcorrencia())
                .nivelSeveridade(request.getNivelSeveridade())
                .dataOcorrencia(request.getDataOcorrencia())
                .descricao(request.getDescricao())
                .usuario(usuario)
                .endereco(endereco)
                .areaOperacao(areaOperacao)
                .build();
    }


    public OcorrenciaResposta converterParaResposta(Ocorrencia ocorrencia) {
        return OcorrenciaResposta.builder()
                .idOcorrencia(ocorrencia.getIdOcorrencia())
                .tipoOcorrencia(ocorrencia.getTipoOcorrencia())
                .nivelSeveridade(ocorrencia.getNivelSeveridade())
                .dataOcorrencia(ocorrencia.getDataOcorrencia())
                .descricao(ocorrencia.getDescricao())
                .nomeUsuario(ocorrencia.getUsuario().getNome())
                .endereco(enderecoMapper.converterParaResposta(ocorrencia.getEndereco()))
                .latitudeCentralArea(ocorrencia.getAreaOperacao() != null ? ocorrencia.getAreaOperacao().getLatitudeCentral() : null)
                .longitudeCentralArea(ocorrencia.getAreaOperacao() != null ? ocorrencia.getAreaOperacao().getLongitudeCentral() : null)
                .build();
    }
}
