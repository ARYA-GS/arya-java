package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.response.PrevisaoRiscoResposta;
import com.arya.api.usecase.imlp.PrevisaoRiscoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class PrevisaoRiscoController {

    private final PrevisaoRiscoService previsaoRiscoService;

    @GetMapping("/previsao-risco")
    public ResponseEntity<PrevisaoRiscoResposta> obterPrevisaoRisco() {
        return ResponseEntity.ok(previsaoRiscoService.preverRisco());
    }
}

