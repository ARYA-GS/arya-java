package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.HubOperacionalCadastroRequest;
import com.arya.api.adapter.http.dto.response.HubOperacionalResposta;
import com.arya.api.usecase.imlp.HubOperacionalServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hubs")
public class HubOperacionalController {

    @Autowired
    private HubOperacionalServiceImpl hubService;


    @PostMapping
    public ResponseEntity<HubOperacionalResposta> cadastrar(@RequestBody @Valid HubOperacionalCadastroRequest request) {
        HubOperacionalResposta resposta = hubService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }


    @GetMapping
    public ResponseEntity<List<HubOperacionalResposta>> listar() {
        List<HubOperacionalResposta> hubs = hubService.listarTodos();
        return ResponseEntity.ok(hubs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<HubOperacionalResposta> buscarPorId(@PathVariable String id) {
        HubOperacionalResposta resposta = hubService.buscarPorId(id);
        return ResponseEntity.ok(resposta);
    }


    @PutMapping("/{id}")
    public ResponseEntity<HubOperacionalResposta> atualizar(@PathVariable String id,
                                                            @RequestBody @Valid HubOperacionalCadastroRequest request) {
        HubOperacionalResposta resposta = hubService.atualizar(id, request);
        return ResponseEntity.ok(resposta);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        hubService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
