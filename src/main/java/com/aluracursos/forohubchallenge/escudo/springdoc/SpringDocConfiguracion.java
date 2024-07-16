package com.aluracursos.forohubchallenge.escudo.springdoc;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;

@Configuration
public class SpringDocConfiguracion {
    @Bean
    public OpenAPI customOpenAPI(ServletContext servletContext) {
        Server server = new Server().url(servletContext.getContextPath());
        
        return new OpenAPI()
                .servers(List.of(server))
                .components(new Components()
                .addSecuritySchemes("bearer-key",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
