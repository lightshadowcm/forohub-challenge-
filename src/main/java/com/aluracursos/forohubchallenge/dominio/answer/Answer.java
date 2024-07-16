package com.aluracursos.forohubchallenge.dominio.answer;

import java.time.LocalDateTime;

import com.aluracursos.forohubchallenge.dominio.topico.Topic;
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

@Table(name = "answers")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;    
    private Boolean solution;


    public Answer(String answer, LocalDateTime creationDate, Topic topic, UserEntity author){
        this.answer = answer;
        this.creationDate = creationDate;
        this.topic = topic;
        this.author = author;
        this.solution = false;
    }

    public void updateAnswer(DataAnswerUpdate dataAnswerUpdate){
        if(dataAnswerUpdate.newMessage() != null){
            this.answer = dataAnswerUpdate.newMessage();
        }
    }


    public void markSolution(){
        this.solution = true;
    }
}
