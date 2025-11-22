package br.com.mentoria.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RespostaRequestDto {
    @NotNull(message = "O ID da pergunta é obrigatório")
    public Integer idPergunta;

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota mínima é 1")
    @Max(value = 5, message = "A nota máxima é 5")
    public Integer nota;
}