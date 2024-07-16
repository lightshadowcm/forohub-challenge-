package com.aluracursos.forohubchallenge.dominio.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aluracursos.forohubchallenge.dominio.answer.DataAnswer;
import com.aluracursos.forohubchallenge.dominio.topico.Topic;
import com.aluracursos.forohubchallenge.dominio.topico.TopicRepository;
import com.aluracursos.forohubchallenge.escudo.errores.IntegrityValidation;

@Component
public class TopicClosed implements TopicValidations{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validateTopic(Object data) {
        Long idTopic = null;

        if(data instanceof Topic){
            idTopic = ((Topic) data).getId();
        }else if(data instanceof DataAnswer){
            idTopic = ((DataAnswer) data).topic();
        }

        if(idTopic == null){
            return;
        }

        System.out.println(idTopic);
        var topic = topicRepository.getReferenceById(idTopic);

        if(!topic.getStatus().booleanValue()){
            throw new IntegrityValidation("El topico se encuentra cerrado");
        }
    }

}
