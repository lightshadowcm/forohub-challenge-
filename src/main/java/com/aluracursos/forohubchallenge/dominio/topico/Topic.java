package com.aluracursos.forohubchallenge.dominio.topico;

import java.time.LocalDateTime;

import com.aluracursos.forohubchallenge.dominio.course.Course;
import com.aluracursos.forohubchallenge.dominio.usuario.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "topics")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column(unique = true)
    private String message;
    private LocalDateTime creationDate;
    private Boolean status;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    private Integer answers;

    public Topic(String title, String message, LocalDateTime creationDate, UserEntity userEntity, Course course){
        this.title = title;
        this.message = message;
        this.creationDate = creationDate;
        this.author = userEntity;
        this.course = course;
        this.answers = 0;
        this.status = true;
    }

    public void updateTopic(DataTopic dataTopic, Course course){
        if(dataTopic.title() != null){
            this.title = dataTopic.title();
        }

        if(dataTopic.message() != null){
            this.message = dataTopic.message();
        }

        if(course != null){
            this.course = course;
        }
    }

    //cerrara un topico y no se podra responder
    public void closeTopic(){
        this.status = false;
    }
}
