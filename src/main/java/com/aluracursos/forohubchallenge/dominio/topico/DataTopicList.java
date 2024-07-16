package com.aluracursos.forohubchallenge.dominio.topico;

import java.time.LocalDateTime;

public record DataTopicList(
    String title,
    String message,
    LocalDateTime creationDate,
    String status,
    String author,
    String course,
    Integer answers
) {
    public DataTopicList(Topic topic){
        this(
            topic.getTitle(),
            topic.getMessage(),
            topic.getCreationDate(),
            topic.getStatus().booleanValue()?"Active":"Closed",
            topic.getAuthor().getName(),
            topic.getCourse().getName(),
            topic.getAnswers()
        );
    }
}
