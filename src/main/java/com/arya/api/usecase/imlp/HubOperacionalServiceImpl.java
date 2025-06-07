package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.HubOperacionalCadastroRequest;
import com.arya.api.adapter.http.dto.response.HubOperacionalResposta;
import com.arya.api.adapter.repository.EnderecoRepository;
import com.arya.api.adapter.repository.HubOperacionalRepository;
import com.arya.api.domain.mapper.EnderecoMapper;
import com.arya.api.domain.mapper.HubOperacionalMapper;
import com.arya.api.domain.model.Endereco;
import com.arya.api.domain.model.HubOperacional;
import com.arya.api.infra.messaging.HubProducer;
import com.arya.api.usecase.service.HubOperacionalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HubOperacionalServiceImpl implements HubOperacionalService {

    @Autowired
    private HubOperacionalRepository hubRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private HubOperacionalMapper hubMapper;

    @Autowired
    private  GeocodingService geocodingService;

    @Autowired
    private HubProducer hubProducer;

    @Override
    public HubOperacionalResposta salvar(HubOperacionalCadastroRequest request) {
        Endereco endereco = enderecoMapper.converterParaModelo(request.getEndereco());

        String enderecoCompleto = String.format("%s, %s, %s, %s",
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getPais()
        );


        Optional<double[]> coordenadas = geocodingService.obterCoordenadas(enderecoCompleto);
        if (coordenadas.isPresent()) {
            double lat = coordenadas.get()[0];
            double lng = coordenadas.get()[1];

            boolean existeNoMesmoPonto = hubRepository.existsByEndereco_LatitudeAndEndereco_Longitude(lat, lng);
            if (existeNoMesmoPonto) {
                throw new IllegalArgumentException("Já existe um Hub Operacional exatamente nesse ponto geográfico.");
            }

            endereco.setLatitude(lat);
            endereco.setLongitude(lng);
        }

        endereco = enderecoRepository.save(endereco);

        HubOperacional hub = hubMapper.converterParaModelo(request, endereco);
        hub = hubRepository.save(hub);

        hubProducer.send(hub);

        return hubMapper.converterParaResposta(hub);
    }

    @Override
    public HubOperacionalResposta buscarPorId(String id) {
        HubOperacional hub = hubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hub operacional não encontrado com id: " + id));

        return hubMapper.converterParaResposta(hub);
    }

    @Override
    public List<HubOperacionalResposta> listarTodos() {
        return hubRepository.findAll().stream()
                .map(hubMapper::converterParaResposta)
                .collect(Collectors.toList());
    }

    @Override
    public HubOperacionalResposta atualizar(String id, HubOperacionalCadastroRequest request) {
        HubOperacional existente = hubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hub operacional não encontrado com id: " + id));

        Endereco novoEndereco = enderecoMapper.converterParaModelo(request.getEndereco());

        String enderecoCompleto = String.format("%s, %s, %s, %s",
                novoEndereco.getBairro(),
                novoEndereco.getCidade(),
                novoEndereco.getEstado(),
                novoEndereco.getPais()
        );

        Optional<double[]> coordenadas = geocodingService.obterCoordenadas(enderecoCompleto);
        if (coordenadas.isPresent()) {
            double[] coord = coordenadas.get();
            novoEndereco.setLatitude(coord[0]);
            novoEndereco.setLongitude(coord[1]);
        }

        novoEndereco = enderecoRepository.save(novoEndereco);

        existente.setNome(request.getNome());
        existente.setStatus(request.getStatus());
        existente.setEndereco(novoEndereco);

        HubOperacional atualizado = hubRepository.save(existente);
        return hubMapper.converterParaResposta(atualizado);
    }

    @Override
    public void deletar(String id) {
        HubOperacional hub = hubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hub operacional não encontrado com id: " + id));

        hubRepository.delete(hub);
    }
}
