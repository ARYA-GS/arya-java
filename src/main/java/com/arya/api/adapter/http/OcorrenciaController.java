package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.OcorrenciaCadastroRequest;
import com.arya.api.adapter.http.dto.response.OcorrenciaResposta;
import com.arya.api.usecase.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @PostMapping
    public ResponseEntity<OcorrenciaResposta> cadastrar(@RequestBody @Valid OcorrenciaCadastroRequest request) {
        OcorrenciaResposta resposta = ocorrenciaService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<OcorrenciaResposta>> listarTodos() {
        return ResponseEntity.ok(ocorrenciaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaResposta> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(ocorrenciaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        ocorrenciaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
