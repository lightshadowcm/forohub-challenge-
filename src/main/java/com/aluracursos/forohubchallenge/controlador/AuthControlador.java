package com.aluracursos.forohubchallenge.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.aluracursos.forohubchallenge.dominio.servicios.UserEntityService;
import com.aluracursos.forohubchallenge.dominio.usuario.DataAuthenticationUser;
import com.aluracursos.forohubchallenge.dominio.usuario.DataRegisterUser;
import com.aluracursos.forohubchallenge.dominio.usuario.UserEntity;
import com.aluracursos.forohubchallenge.escudo.seguridad.DataJwtToken;
import com.aluracursos.forohubchallenge.escudo.seguridad.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthControlador {
    private AuthenticationManager authManager;
    private TokenService tokenService;
    private UserEntityService userEntityService;

    @Autowired
    public AuthControlador(AuthenticationManager authManager, TokenService tokenService, UserEntityService userEntityService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.userEntityService = userEntityService;
    }

    @PostMapping("/login")
    public ResponseEntity authUser(@RequestBody @Valid DataAuthenticationUser dataAuthUser){
        Authentication authToken = new UsernamePasswordAuthenticationToken(dataAuthUser.email(), dataAuthUser.password());
        var userAuthenticated = authManager.authenticate(authToken);
        var jwtToken = tokenService.generateToken((UserEntity) userAuthenticated.getPrincipal());

        return ResponseEntity.ok(new DataJwtToken(jwtToken));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity registerUser(@RequestBody @Valid DataRegisterUser dataRegisterUser, UriComponentsBuilder uriBuilder){
        var response = userEntityService.registerUser(dataRegisterUser);

        if(Boolean.FALSE.equals(response)){
            return ResponseEntity.badRequest().body("correo ya esta en uso ");
        }

        return ResponseEntity.ok("El usuario ha sido creado");
    }
}
