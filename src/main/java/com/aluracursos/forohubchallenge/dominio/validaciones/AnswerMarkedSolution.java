package com.aluracursos.forohubchallenge.dominio.validaciones;

import org.springframework.stereotype.Component;

import com.aluracursos.forohubchallenge.dominio.answer.Answer;
import com.aluracursos.forohubchallenge.escudo.errores.IntegrityValidation;

@Component
public class AnswerMarkedSolution implements AnswerValidations{

    @Override
    public void validateAnswer(Object data) {
        if(data instanceof Answer){
            Answer answer = (Answer) data;

            if(answer.getSolution().booleanValue()){
                throw new IntegrityValidation("La respuesta ya ha sido marcada");
            }
        }
    }

}
