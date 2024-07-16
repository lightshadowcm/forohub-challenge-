package com.aluracursos.forohubchallenge.dominio.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aluracursos.forohubchallenge.dominio.answer.AnswerRepository;
import com.aluracursos.forohubchallenge.dominio.answer.DataAnswer;
import com.aluracursos.forohubchallenge.dominio.answer.DataAnswerUpdate;
import com.aluracursos.forohubchallenge.escudo.errores.IntegrityValidation;

@Component
public class AnswerContentExists implements AnswerValidations{
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void validateAnswer(Object data) {
        Long idTopic = null;
        String message = null;

        if(data instanceof DataAnswer){
            var dataAnswer = (DataAnswer) data;
            idTopic = dataAnswer.topic();
            message = dataAnswer.message();
        }else if(data instanceof DataAnswerUpdate){
            var dataAnswerUpdate = (DataAnswerUpdate) data;
            var idAnswer = dataAnswerUpdate.idAnswer();
            var answerData = answerRepository.getReferenceById(idAnswer);
            idTopic = answerData.getTopic().getId();
            message = dataAnswerUpdate.newMessage();
        }


        var messageExist = answerRepository.findByTopicMessage(idTopic, message);

        if(messageExist.isPresent()){
            throw new IntegrityValidation("La respuesta ya existe");
        }
    }

}
