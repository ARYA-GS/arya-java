package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.response.PrevisaoRiscoResposta;
import com.arya.api.usecase.imlp.PrevisaoRiscoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ia")
public class PrevisaoRiscoController {

    @Autowired
    private PrevisaoRiscoService previsaoRiscoService;

    @GetMapping("/previsao-risco")
    public ResponseEntity<PrevisaoRiscoResposta> obterPrevisaoRisco() {
        return ResponseEntity.ok(previsaoRiscoService.preverRisco());
    }
}

