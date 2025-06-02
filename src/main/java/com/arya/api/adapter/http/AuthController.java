//package com.arya.api.adapter.http;
//
//import com.arya.api.adapter.http.dto.request.LoginRequest;
//import com.arya.api.usecase.service.UsuarioService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final UsuarioService usuarioService;
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
//        String token = usuarioService.validarLogin(request.getEmail(), request.getSenha());
//        return ResponseEntity.ok(token);
//    }
//}
