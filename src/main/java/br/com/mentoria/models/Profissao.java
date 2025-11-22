package br.com.mentoria.models;

public class Profissao {
    private int idProfissao;
    private String nomeProfissao;

    public Profissao() {}

    public Profissao(int idProfissao, String nomeProfissao) {
        this.idProfissao = idProfissao;
        this.nomeProfissao = nomeProfissao;
    }

    public int getIdProfissao() { return idProfissao; }
    public void setIdProfissao(int idProfissao) { this.idProfissao = idProfissao; }
    public String getNomeProfissao() { return nomeProfissao; }
    public void setNomeProfissao(String nomeProfissao) { this.nomeProfissao = nomeProfissao; }
}