package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.DroneModel;
import com.arya.api.adapter.http.dto.response.SugestaoDroneResposta;
import com.arya.api.adapter.repository.OcorrenciaRepository;
import com.arya.api.domain.model.Ocorrencia;
import com.arya.api.usecase.imlp.DroneInteligenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class DroneInteligenciaController {

    private final DroneInteligenciaService droneInteligenciaService;
    private final OcorrenciaRepository ocorrenciaRepository;

    @PostMapping("/sugerir-drone/{idOcorrencia}")
    public ResponseEntity<SugestaoDroneResposta> sugerirDrone(
            @PathVariable String idOcorrencia,
            @RequestBody List<DroneModel> drones) {

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(idOcorrencia)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ocorrência não encontrada"));

        var sugestao = droneInteligenciaService.sugerirDroneIdeal(ocorrencia, drones);

        return ResponseEntity.ok(sugestao);
    }
}
