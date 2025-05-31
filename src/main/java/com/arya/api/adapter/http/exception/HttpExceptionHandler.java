package com.arya.api.adapter.http.exception;

import com.arya.api.adapter.http.dto.erros.ResponseErrors;
import com.arya.api.domain.exception.EmailJaCadastradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarErrosDeValidacaoFisica(MethodArgumentNotValidException exception){

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<ResponseErrors> listaDeErro = new ArrayList<>();

        for (FieldError fieldError : fieldErrors){

            String campo = fieldError.getField();
            String mensagemDeErro = fieldError.getDefaultMessage();

            ResponseErrors responseErro = new ResponseErrors();
            responseErro.setCampo(campo);
            responseErro.setMensagem(mensagemDeErro);

            listaDeErro.add(responseErro);

        }

        return ResponseEntity.badRequest().body(listaDeErro);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<String> handleEmailJaCadastrado(EmailJaCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
