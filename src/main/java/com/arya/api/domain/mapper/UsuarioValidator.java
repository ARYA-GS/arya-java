package com.arya.api.domain.mapper;

import org.springframework.stereotype.Component;

@Component
public class UsuarioValidator {

    public String validarETrocarSenha(String senhaAtualSalva, String senhaInformada, String novaSenha) {
        if (senhaAtualSalva == null || senhaInformada == null || novaSenha == null) {
            throw new IllegalArgumentException("Senha salva, senha informada e nova senha não podem ser nulas");
        }

        if (!senhaAtualSalva.equals(senhaInformada)) {
            throw new IllegalArgumentException("Senha atual informada está incorreta");
        }

        if (senhaAtualSalva.equals(novaSenha)) {
            throw new IllegalArgumentException("A nova senha deve ser diferente da senha atual");
        }

        return novaSenha;
    }
}
