package com.aluracursos.forohubchallenge.dominio.answer;

import java.time.LocalDateTime;

public record DataSavedAnswer(
    Long idAnswer,
    String message,
    String topicTitle,
    LocalDateTime creationDate
) {
    public DataSavedAnswer(Answer answer){
        this(
            answer.getId(),
            answer.getAnswer(),
            answer.getTopic().getTitle(),
            answer.getCreationDate()
        );
    }
}
