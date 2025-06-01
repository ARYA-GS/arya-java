package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.OcorrenciaCadastroRequest;
import com.arya.api.adapter.http.dto.response.OcorrenciaResposta;
import com.arya.api.adapter.repository.AreaOperacaoRepository;
import com.arya.api.adapter.repository.EnderecoRepository;
import com.arya.api.adapter.repository.OcorrenciaRepository;
import com.arya.api.adapter.repository.UsuarioRepository;
import com.arya.api.domain.mapper.EnderecoMapper;
import com.arya.api.domain.mapper.OcorrenciaMapper;
import com.arya.api.domain.model.AreaOperacao;
import com.arya.api.domain.model.Endereco;
import com.arya.api.domain.model.Ocorrencia;
import com.arya.api.domain.model.Usuario;
import com.arya.api.usecase.service.OcorrenciaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcorrenciaServiceImpl implements OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private AreaOperacaoRepository areaOperacaoRepository;

    @Autowired
    private OcorrenciaMapper ocorrenciaMapper;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private GeocodingService geocodingService;

    @Override
    public OcorrenciaResposta salvar(OcorrenciaCadastroRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Endereco endereco = enderecoMapper.converterParaModelo(request.getEndereco());

        String enderecoCompleto = String.format("%s, %s, %s, %s",
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getPais()
        );

        final Endereco enderecoFinal = endereco;

        geocodingService.obterCoordenadas(enderecoCompleto)
                .ifPresent(coord -> {
                    enderecoFinal.setLatitude(coord[0]);
                    enderecoFinal.setLongitude(coord[1]);
                });

        endereco = enderecoRepository.save(enderecoFinal);

        final Endereco finalEndereco = endereco;
        AreaOperacao area = areaOperacaoRepository.findAll().stream()
                .filter(a -> isDentroDaArea(
                        finalEndereco.getLatitude(),
                        finalEndereco.getLongitude(),
                        a.getLatitudeCentral(),
                        a.getLongitudeCentral()
                ))
                .findFirst()
                .orElseGet(() -> {
                    AreaOperacao novaArea = new AreaOperacao();
                    novaArea.setLatitudeCentral(finalEndereco.getLatitude());
                    novaArea.setLongitudeCentral(finalEndereco.getLongitude());
                    return areaOperacaoRepository.save(novaArea);
                });

        Ocorrencia ocorrencia = ocorrenciaMapper.converterParaModelo(request, usuario, endereco, area);
        ocorrencia = ocorrenciaRepository.save(ocorrencia);

        return ocorrenciaMapper.converterParaResposta(ocorrencia);
    }

    @Override
    public List<OcorrenciaResposta> listarTodos() {
        return ocorrenciaRepository.findAll().stream()
                .map(ocorrenciaMapper::converterParaResposta)
                .toList();
    }

    @Override
    public OcorrenciaResposta buscarPorId(String id) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ocorrência não encontrada"));
        return ocorrenciaMapper.converterParaResposta(ocorrencia);
    }

    @Override
    public void deletar(String id) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ocorrência não encontrada"));
        ocorrenciaRepository.delete(ocorrencia);
    }

    private boolean isDentroDaArea(Double latEndereco, Double lonEndereco, Double latArea, Double lonArea) {
        double raioKm = 5.0;
        double distancia = calcularDistanciaKm(latEndereco, lonEndereco, latArea, lonArea);
        return distancia <= raioKm;
    }

    private double calcularDistanciaKm(Double lat1, Double lon1, Double lat2, Double lon2) {
        double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
