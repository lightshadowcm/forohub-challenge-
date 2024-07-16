package com.aluracursos.forohubchallenge.escudo.seguridad;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
