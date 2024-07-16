package com.aluracursos.forohubchallenge.dominio.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.aluracursos.forohubchallenge.dominio.answer.Answer;
import com.aluracursos.forohubchallenge.dominio.topico.Topic;
import com.aluracursos.forohubchallenge.dominio.usuario.UserRepository;
import com.aluracursos.forohubchallenge.escudo.errores.IntegrityValidation;
import com.aluracursos.forohubchallenge.escudo.seguridad.IAuthenticationFacade;

@Component
public class UserLoggedToTest implements GeneralValidations{
    private IAuthenticationFacade authFacade;
    private UserRepository userRepository;

    @Autowired
    public UserLoggedToTest(IAuthenticationFacade authFacade, UserRepository userRepository) {
        this.authFacade = authFacade;
        this.userRepository = userRepository;
    }
    
    private Long loggedUserId() {
        Authentication auth = authFacade.getAuthentication();

        return userRepository.findIdByName(auth.getName());
    }

    @Override
    public void validateGeneral(Object data) {
        Long dataToTest = null;

        if(data instanceof Topic){
            var topicData = (Topic) data;
            dataToTest = topicData.getAuthor().getId();
        }else if(data instanceof Answer){
            var answerData = (Answer) data;
            dataToTest = answerData.getAuthor().getId();
        }
        
        if(!dataToTest.equals(loggedUserId())){
            throw new IntegrityValidation("El usuario no puede realizar esta accion");
        }
    }
}
