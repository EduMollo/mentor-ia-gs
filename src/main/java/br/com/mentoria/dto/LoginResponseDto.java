package br.com.mentoria.dto;

public class LoginResponseDto {
    public String token;
    public String nomeUsuario;
    public String email;

    public LoginResponseDto(String token, String nomeUsuario, String email) {
        this.token = token;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
    }
}