package com.aluracursos.forohubchallenge.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluracursos.forohubchallenge.dominio.servicios.UserEntityService;
import com.aluracursos.forohubchallenge.dominio.usuario.DataRegisterUser;
import com.aluracursos.forohubchallenge.dominio.usuario.DataUserList;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserEntityControlador {
    @Autowired
    private UserEntityService userService;

    @GetMapping
    public ResponseEntity<Page<DataUserList>> userAll(@PageableDefault(size = 5, sort = "name") Pageable pageable){
        return ResponseEntity.ok(userService.listUsers(pageable));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity userDelete(@PathVariable @Valid Long id){
        userService.deleteUser(id);

        return ResponseEntity.ok("El usuario ha sido eliminado...");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity userUpdate(@PathVariable @Valid Long id, @RequestBody @Valid DataRegisterUser dataRegisterUser){
        var userResponse = userService.updateUser(id, dataRegisterUser);

        return ResponseEntity.ok(userResponse);
    }
}
