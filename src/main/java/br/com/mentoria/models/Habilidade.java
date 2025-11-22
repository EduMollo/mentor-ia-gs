package br.com.mentoria.models;

public class Habilidade {
    private int idHabilidade;
    private String nomeHabilidade;
    private String descricaoHabilidade;

    public Habilidade() {}

    public Habilidade(int idHabilidade, String nomeHabilidade, String descricaoHabilidade) {
        this.idHabilidade = idHabilidade;
        this.nomeHabilidade = nomeHabilidade;
        this.descricaoHabilidade = descricaoHabilidade;
    }

    public int getIdHabilidade() { return idHabilidade; }
    public void setIdHabilidade(int idHabilidade) { this.idHabilidade = idHabilidade; }
    public String getNomeHabilidade() { return nomeHabilidade; }
    public void setNomeHabilidade(String nomeHabilidade) { this.nomeHabilidade = nomeHabilidade; }
    public String getDescricaoHabilidade() { return descricaoHabilidade; }
    public void setDescricaoHabilidade(String descricaoHabilidade) { this.descricaoHabilidade = descricaoHabilidade; }
}