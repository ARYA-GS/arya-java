package com.arya.api.adapter.http;

import com.arya.api.adapter.http.dto.request.PerguntaRequest;
import com.arya.api.adapter.http.dto.response.RespostaNaturalIA;
import com.arya.api.usecase.imlp.PerguntaIaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ia")
public class PerguntaIaController {

    @Autowired
    private PerguntaIaService perguntaIaService;

    @PostMapping("/perguntar")
    public ResponseEntity<RespostaNaturalIA> perguntar(@RequestBody PerguntaRequest request) {
        return ResponseEntity.ok(perguntaIaService.responder(request));
    }
}
