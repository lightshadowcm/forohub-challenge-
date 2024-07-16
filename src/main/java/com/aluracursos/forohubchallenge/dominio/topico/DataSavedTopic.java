package com.aluracursos.forohubchallenge.dominio.topico;

import java.time.LocalDateTime;

public record DataSavedTopic(
    Long id,
    String title,
    String message,
    LocalDateTime createdDate
) {
    public DataSavedTopic(Topic topic){
        this(
            topic.getId(),
            topic.getTitle(),
            topic.getMessage(),
            topic.getCreationDate()
        );
    }
}
