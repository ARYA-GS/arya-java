package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.response.DroneResposta;
import com.arya.api.adapter.http.dto.response.SugestaoDroneResposta;
import com.arya.api.adapter.repository.OcorrenciaRepository;
import com.arya.api.domain.model.Ocorrencia;
import com.arya.api.usecase.imlp.DroneInteligenciaService;
import com.arya.api.usecase.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ia")
public class DroneInteligenciaController {

    @Autowired
    private DroneService droneService;

    @Autowired
    private DroneInteligenciaService droneInteligenciaService;

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @GetMapping("/sugerir-drone/{idOcorrencia}")
    public ResponseEntity<SugestaoDroneResposta> sugerirDrone(@PathVariable String idOcorrencia) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(idOcorrencia)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ocorrência não encontrada"));

        List<DroneResposta> drones = droneService.listarTodos();

        var sugestao = droneInteligenciaService.sugerirDroneIdeal(ocorrencia, drones);

        return ResponseEntity.ok(sugestao);
    }
}
