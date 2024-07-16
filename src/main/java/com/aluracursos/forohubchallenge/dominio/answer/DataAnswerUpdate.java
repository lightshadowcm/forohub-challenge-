package com.aluracursos.forohubchallenge.dominio.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataAnswerUpdate(
    @NotNull Long idAnswer,
    @NotBlank String newMessage
) {

}
