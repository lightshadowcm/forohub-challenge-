package com.aluracursos.forohubchallenge.dominio.servicios;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aluracursos.forohubchallenge.dominio.perfil.ProfileRepository;
import com.aluracursos.forohubchallenge.dominio.usuario.DataRegisterUser;
import com.aluracursos.forohubchallenge.dominio.usuario.DataUserList;
import com.aluracursos.forohubchallenge.dominio.usuario.UserEntity;
import com.aluracursos.forohubchallenge.dominio.usuario.UserRepository;
import com.aluracursos.forohubchallenge.escudo.errores.IntegrityValidation;
import jakarta.validation.Valid;

@Service
public class UserEntityService {
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserEntityService(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean registerUser(DataRegisterUser dataRegisterUser){
        if(dataRegisterUser.email() != null && userRepository.existsByEmail(dataRegisterUser.email()).booleanValue()){
            return false;
        }

        var dataProfile = profileRepository.findByName("ROLE_USER");
        var newUser = new UserEntity(
            dataRegisterUser.name(),
            dataRegisterUser.email(),
            passwordEncoder.encode(dataRegisterUser.password()),
            Set.of(dataProfile)
        );

        userRepository.save(newUser);

        return true;
    }

    public Page<DataUserList> listUsers(Pageable pageable){
        return userRepository.findAll(pageable).map(DataUserList::new);
    }

    public void deleteUser(@Valid Long id) {
        if(userIsEnabledAndExist(id)){
            throw new IntegrityValidation("El usuario a eliminar no existe...");
        }

        var user = userRepository.getReferenceById(id);
        user.deactivateUser();
    }

    public UserEntity updateUser(@Valid Long id, @Valid DataRegisterUser dataRegisterUser) {
        if(userIsEnabledAndExist(id)){
            throw new IntegrityValidation("El usuario a actualizar no existe...");
        }
        
        //validar que el correo no exista
        if(userRepository.existsByEmail(dataRegisterUser.email()).booleanValue()){
            throw new IntegrityValidation("El correo ya existe...");
        }
        
        UserEntity userEntity = userRepository.getReferenceById(id);
        userEntity.updateUser(
            dataRegisterUser.name(),
            dataRegisterUser.email(),
            passwordEncoder.encode(dataRegisterUser.password())
        );

        return userEntity;
    }

    private boolean userIsEnabledAndExist(Long id){
        var userExist = userRepository.findById(id);

        //validar que exista el id o este habilitado
        return (!userExist.isPresent() || !userExist.get().isEnabled());
    }
}
