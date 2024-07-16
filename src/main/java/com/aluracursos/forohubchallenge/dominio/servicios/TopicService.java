package com.aluracursos.forohubchallenge.dominio.servicios;

import java.time.LocalDateTime;
import java.util.List;

import com.aluracursos.forohubchallenge.dominio.validaciones.GeneralValidations;
import com.aluracursos.forohubchallenge.dominio.validaciones.TopicValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aluracursos.forohubchallenge.dominio.course.Course;
import com.aluracursos.forohubchallenge.dominio.course.CourseRepository;
import com.aluracursos.forohubchallenge.dominio.topico.DataTopic;
import com.aluracursos.forohubchallenge.dominio.topico.DataTopicList;
import com.aluracursos.forohubchallenge.dominio.topico.DataSavedTopic;
import com.aluracursos.forohubchallenge.dominio.topico.Topic;
import com.aluracursos.forohubchallenge.dominio.topico.TopicRepository;
import com.aluracursos.forohubchallenge.dominio.usuario.UserEntity;
import com.aluracursos.forohubchallenge.dominio.usuario.UserRepository;
import com.aluracursos.forohubchallenge.escudo.errores.IntegrityValidation;
import jakarta.validation.Valid;

@Service
public class TopicService {
    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private List<TopicValidations> topicValidations;
    private List<GeneralValidations> generalValidations;

    @Autowired
    public TopicService(TopicRepository topicRepository, UserRepository userRepository,
            CourseRepository courseRepository, List<TopicValidations> topicValidations, List<GeneralValidations> generalValidations) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.topicValidations = topicValidations;
        this.generalValidations = generalValidations;
    }

    public DataSavedTopic addNewTopic(DataTopic dataNewTopic) {

        var methodCourse = getSelectedCourse(dataNewTopic);
        var methodUser = getSelectedUser(dataNewTopic);
        

        topicValidations.forEach(tv -> tv.validateTopic(dataNewTopic));

        var currentDate = LocalDateTime.now();        
        var newTopic = new Topic(
            dataNewTopic.title(),
            dataNewTopic.message(),
            currentDate,
            methodUser,
            methodCourse
        );

        topicRepository.save(newTopic);
        
        return new DataSavedTopic(newTopic);
    }

    public Topic updateTopic(Long id, @Valid DataTopic dataUpdateTopic) {

        if(!topicRepository.existsById(id)){
            throw new IntegrityValidation("El topico a actualizar no ha sido encontrado...");
        }
        

        if(!topicRepository.findByIdAndAuthorId(id, dataUpdateTopic.author()).isPresent()){
            throw new IntegrityValidation("El autor no corresponde al topico a actualizar...");
        }


        topicValidations.forEach(tv -> tv.validateTopic(dataUpdateTopic));


        var methodCourse = getSelectedCourse(dataUpdateTopic);
        var methodUser = getSelectedUser(dataUpdateTopic);


        Topic updateTopic = topicRepository.getReferenceById(id);
        updateTopic.updateTopic(dataUpdateTopic, methodCourse);


        return updateTopic;
    }

    public String deleteTopic(Long id){
        var topicToDelete = topicRepository.findById(id);


        if(!topicToDelete.isPresent()){
            throw new IntegrityValidation("El topico a eliminar no ha sido encontrado...");
        }


        generalValidations.forEach(gv -> gv.validateGeneral(topicToDelete.get()));


        topicRepository.deleteById(id);


        return "El topico ha sido eliminado";
    }

    public Page<DataTopicList> listTopic(Pageable pageable){
        return topicRepository.findAll(pageable).map(DataTopicList::new);
    }

    public DataTopicList detailTopic(@Valid Long id) {

        if(!topicRepository.existsById(id)){
            throw new IntegrityValidation("El topico a consultar no ha sido encontrado");
        }

        return new DataTopicList(topicRepository.getReferenceById(id));
    }

    public String closeTopic(@Valid Long id) {
        if(id == null){
            throw new IntegrityValidation("Ha ocurrido un problema con el paramatro enviado");
        }

        if(!topicRepository.existsById(id)){
            throw new IntegrityValidation("El topico a cerrar no ha sido encontrado");
        }

        var topicData = topicRepository.getReferenceById(id);

        generalValidations.forEach(gv -> gv.validateGeneral(topicData));
        topicValidations.forEach(tv -> tv.validateTopic(topicData));

        topicData.closeTopic();

        return "Se ha cerradp el topico";
    }

    private Course getSelectedCourse(DataTopic data){
        if(!courseRepository.existsById(data.course())){
            throw new IntegrityValidation("El curso ingresado es invalido");
        }

        return courseRepository.getReferenceById(data.course());
    }

    private UserEntity getSelectedUser(DataTopic data){
        if(!userRepository.existsById(data.author())){
            throw new IntegrityValidation("El usuario ingresado es invalido");
        }

        return userRepository.getReferenceById(data.author());
    }
}
