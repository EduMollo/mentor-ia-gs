package br.com.mentoria.models;

import java.time.LocalDate;

public class Teste {
    private int idTeste;
    private LocalDate dataTeste;
    private Usuario usuario;

    public Teste() {}

    public Teste(int idTeste, LocalDate dataTeste, Usuario usuario) {
        this.idTeste = idTeste;
        this.dataTeste = dataTeste;
        this.usuario = usuario;
    }

    public int getIdTeste() { return idTeste; }
    public void setIdTeste(int idTeste) { this.idTeste = idTeste; }
    public LocalDate getDataTeste() { return dataTeste; }
    public void setDataTeste(LocalDate dataTeste) { this.dataTeste = dataTeste; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}