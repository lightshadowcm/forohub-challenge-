package com.aluracursos.forohubchallenge.dominio.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aluracursos.forohubchallenge.dominio.topico.DataTopic;
import com.aluracursos.forohubchallenge.dominio.topico.TopicRepository;
import jakarta.validation.ValidationException;

@Component
public class TopicContentExists implements TopicValidations{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validateTopic(Object data) {
        if(data instanceof DataTopic){
            DataTopic dnt = (DataTopic) data;

            if(dnt.title() == null || dnt.message() == null || dnt.author() == null || dnt.course() == null){
                return;
            }

            var topicTitle = topicRepository.findByTitle(dnt.title().replace(" ", ""));
            var topicMessage = topicRepository.findByMessage(dnt.message().replace(" ", ""));

            if(topicTitle != null){
                throw new ValidationException("El titulo ingresado ya existe");
            }

            if(topicMessage != null){
                throw new ValidationException("El mensaje ingresado ya existe");
            }
        }
    }
}
