package br.com.mentoria.models;

public class Pergunta {
    private int idPergunta;
    private String textoPergunta;
    private Habilidade habilidade;

    public Pergunta() {}

    public Pergunta(int idPergunta, String textoPergunta, Habilidade habilidade) {
        this.idPergunta = idPergunta;
        this.textoPergunta = textoPergunta;
        this.habilidade = habilidade;
    }

    public int getIdPergunta() { return idPergunta; }
    public void setIdPergunta(int idPergunta) { this.idPergunta = idPergunta; }
    public String getTextoPergunta() { return textoPergunta; }
    public void setTextoPergunta(String textoPergunta) { this.textoPergunta = textoPergunta; }
    public Habilidade getHabilidade() { return habilidade; }
    public void setHabilidade(Habilidade habilidade) { this.habilidade = habilidade; }
}