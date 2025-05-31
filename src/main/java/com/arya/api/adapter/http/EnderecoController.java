package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.EnderecoCadastroRequest;
import com.arya.api.adapter.http.dto.response.EnderecoResposta;
import com.arya.api.domain.mapper.EnderecoMapper;
import com.arya.api.domain.model.Endereco;
import com.arya.api.usecase.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enderecos")

public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoMapper enderecoMapper;


    @PostMapping
    public ResponseEntity<EnderecoResposta> cadastrar(@Valid @RequestBody EnderecoCadastroRequest request) {
        Endereco endereco = enderecoMapper.converterParaModelo(request);
        Endereco salvo = enderecoService.salvar(endereco);
        return ResponseEntity.ok(enderecoMapper.converterParaResposta(salvo));
    }

    @GetMapping
    public ResponseEntity<List<EnderecoResposta>> listarTodos() {
        List<Endereco> enderecos = enderecoService.listarTodos();
        List<EnderecoResposta> resposta = enderecos.stream()
                .map(enderecoMapper::converterParaResposta)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResposta> buscarPorId(@PathVariable String id) {
        return enderecoService.buscarPorId(id)
                .map(enderecoMapper::converterParaResposta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResposta> atualizar(@PathVariable String id,
                                                      @Valid @RequestBody EnderecoCadastroRequest request) {
        Endereco enderecoAtualizado = enderecoMapper.converterParaModelo(request);
        Endereco atualizado = enderecoService.atualizar(id, enderecoAtualizado);
        return ResponseEntity.ok(enderecoMapper.converterParaResposta(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}