package br.com.mentoria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDto {
    @NotBlank(message = "Nome é obrigatório")
    public String primeiroNome;

    @NotBlank(message = "Sobrenome é obrigatório")
    public String sobrenome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    public String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    public String senha;
}