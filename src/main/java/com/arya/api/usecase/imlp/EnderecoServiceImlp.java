package com.arya.api.usecase.imlp;

import com.arya.api.adapter.repository.EnderecoRepository;
import com.arya.api.domain.model.Endereco;
import com.arya.api.usecase.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImlp implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private GeocodingService geocodingService;

    @Override
    public Endereco salvar(Endereco endereco) {

        String enderecoCompleto = String.format("%s, %s, %s",
                endereco.getCidade(), endereco.getEstado(), endereco.getPais());

        if (endereco.getLatitude() == null || endereco.getLongitude() == null) {
            geocodingService.obterCoordenadas(enderecoCompleto).ifPresent(coords -> {
                endereco.setLatitude(coords[0]);
                endereco.setLongitude(coords[1]);
            });
        }

        return enderecoRepository.save(endereco);
    }


    @Override
    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    @Override
    public Optional<Endereco> buscarPorId(String id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public Endereco atualizar(String id, Endereco endereco) {
        Endereco enderecoExistente = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com ID: " + id));

        enderecoExistente.setBairro(endereco.getBairro());
        enderecoExistente.setCidade(endereco.getCidade());
        enderecoExistente.setEstado(endereco.getEstado());
        enderecoExistente.setPais(endereco.getPais());
        enderecoExistente.setLatitude(endereco.getLatitude());
        enderecoExistente.setLongitude(endereco.getLongitude());

        return enderecoRepository.save(enderecoExistente);
    }

    @Override
    public void deletar(String id) {
        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado com ID: " + id);
        }
        enderecoRepository.deleteById(id);
    }
}

