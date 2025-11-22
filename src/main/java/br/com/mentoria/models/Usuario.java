package br.com.mentoria.models;

public class Usuario {
    private int idUsuario;
    private String primeiroNome;
    private String sobrenome;
    private String email;
    private String hashSenha;

    public Usuario() {}

    public Usuario(String primeiroNome, String sobrenome, String email, String hashSenha) {
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.hashSenha = hashSenha;
    }

    public Usuario(int idUsuario, String primeiroNome, String sobrenome, String email, String hashSenha) {
        this.idUsuario = idUsuario;
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.hashSenha = hashSenha;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getPrimeiroNome() { return primeiroNome; }
    public void setPrimeiroNome(String primeiroNome) { this.primeiroNome = primeiroNome; }
    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getHashSenha() { return hashSenha; }
    public void setHashSenha(String hashSenha) { this.hashSenha = hashSenha; }
}