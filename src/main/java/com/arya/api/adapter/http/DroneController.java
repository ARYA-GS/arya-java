package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.DroneCadastroRequest;
import com.arya.api.adapter.http.dto.response.DroneResposta;
import com.arya.api.usecase.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping
    public ResponseEntity<DroneResposta> cadastrar(@RequestBody @Valid DroneCadastroRequest request) {
        DroneResposta resposta = droneService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<DroneResposta>> listarTodos() {
        return ResponseEntity.ok(droneService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DroneResposta> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(droneService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DroneResposta> atualizar(@PathVariable String id, @RequestBody @Valid DroneCadastroRequest request) {
        return ResponseEntity.ok(droneService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        droneService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
