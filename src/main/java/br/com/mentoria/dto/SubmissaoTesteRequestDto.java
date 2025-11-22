package br.com.mentoria.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class SubmissaoTesteRequestDto {
    @NotEmpty(message = "A lista de respostas não pode estar vazia")
    public List<RespostaRequestDto> respostas;
}