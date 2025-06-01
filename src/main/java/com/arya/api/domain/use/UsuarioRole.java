package com.arya.api.domain.use;

public enum UsuarioRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
