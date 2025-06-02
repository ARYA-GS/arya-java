package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.PerguntaRequest;
import com.arya.api.adapter.http.dto.response.RespostaNaturalIA;
import com.arya.api.usecase.imlp.PerguntaIaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class PerguntaIaController {

    private final PerguntaIaService perguntaIaService;

    @PostMapping("/perguntar")
    public ResponseEntity<RespostaNaturalIA> perguntar(@RequestBody PerguntaRequest request) {
        return ResponseEntity.ok(perguntaIaService.responder(request));
    }
}
