package com.aluracursos.forohubchallenge.dominio.usuario;

import jakarta.validation.constraints.Email;

public record DataAuthenticationUser(
    @Email String email,
    String password
) {

}
