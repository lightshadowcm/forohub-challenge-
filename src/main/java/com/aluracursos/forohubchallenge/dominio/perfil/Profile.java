package com.aluracursos.forohubchallenge.dominio.perfil;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.aluracursos.forohubchallenge.dominio.usuario.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "profiles")
@Entity(name = "Profile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore //previene problema inifinite recursion en el json
    @ManyToMany(mappedBy = "profiles")
    private Set<UserEntity> users;
}