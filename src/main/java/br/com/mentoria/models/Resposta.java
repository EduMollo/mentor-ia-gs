package br.com.mentoria.models;

public class Resposta {
    private int idResposta;
    private Teste teste;
    private Pergunta pergunta;
    private int valorResposta;

    public Resposta() {}

    public Resposta(int idResposta, Teste teste, Pergunta pergunta, int valorResposta) {
        this.idResposta = idResposta;
        this.teste = teste;
        this.pergunta = pergunta;
        this.valorResposta = valorResposta;
    }

    public int getIdResposta() { return idResposta; }
    public void setIdResposta(int idResposta) { this.idResposta = idResposta; }
    public Teste getTeste() { return teste; }
    public void setTeste(Teste teste) { this.teste = teste; }
    public Pergunta getPergunta() { return pergunta; }
    public void setPergunta(Pergunta pergunta) { this.pergunta = pergunta; }
    public int getValorResposta() { return valorResposta; }
    public void setValorResposta(int valorResposta) { this.valorResposta = valorResposta; }
}