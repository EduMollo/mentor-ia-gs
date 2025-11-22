package br.com.mentoria.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {
    @NotBlank(message = "O email é obrigatório")
    public String email;

    @NotBlank(message = "A senha é obrigatória")
    public String senha;
}