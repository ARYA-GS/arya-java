package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.UsuarioCadastroRequest;
import com.arya.api.adapter.http.dto.request.UsuarioResetarSenhaRequest;
import com.arya.api.adapter.http.dto.request.UsuarioTrocarSenhaRequest;
import com.arya.api.adapter.http.dto.response.UsuarioResposta;
import com.arya.api.domain.mapper.UsuarioMapper;
import com.arya.api.domain.model.Usuario;
import com.arya.api.usecase.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;


    @PostMapping
    public ResponseEntity<UsuarioResposta> cadastrar(@Valid @RequestBody UsuarioCadastroRequest request) {
        Usuario usuario = usuarioMapper.converterParaModelo(request);

        Usuario salvo = usuarioService.salvar(usuario);
        log.info("Usuário cadastrado com sucesso: {}", salvo.getUsuarioId());
        return ResponseEntity.ok(usuarioMapper.converterParaResposta(salvo));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResposta>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioResposta> resposta = usuarios.stream()
                .map(usuarioMapper::converterParaResposta)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResposta> buscarPorId(@PathVariable String id) {
        return usuarioService.buscarPorId(id)
                .map(usuarioMapper::converterParaResposta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/senha")
    public ResponseEntity<UsuarioResposta> trocarSenha(
            @PathVariable String id,
            @Valid @RequestBody UsuarioTrocarSenhaRequest request) {

        Usuario atualizado = usuarioService.trocarSenha(id, request);
        return ResponseEntity.ok(usuarioMapper.converterParaResposta(atualizado));
    }


    @PatchMapping("/resetar-senha")
    public ResponseEntity<Void> resetarSenhaPorEmail(@RequestBody @Valid UsuarioResetarSenhaRequest request) {
        usuarioService.resetarSenhaPorEmail(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/existe-email")
    public ResponseEntity<Boolean> existeEmail(@RequestParam String email) {
        boolean existe = usuarioService.buscarPorEmail(email).isPresent();
        return ResponseEntity.ok(existe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
