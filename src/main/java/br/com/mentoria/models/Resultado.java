package br.com.mentoria.models;

public class Resultado {
    private int idResultado;
    private Teste teste;
    private Profissao profissao1;
    private Profissao profissao2;
    private Profissao profissao3;

    public Resultado() {}

    public int getIdResultado() { return idResultado; }
    public void setIdResultado(int idResultado) { this.idResultado = idResultado; }
    public Teste getTeste() { return teste; }
    public void setTeste(Teste teste) { this.teste = teste; }
    public Profissao getProfissao1() { return profissao1; }
    public void setProfissao1(Profissao profissao1) { this.profissao1 = profissao1; }
    public Profissao getProfissao2() { return profissao2; }
    public void setProfissao2(Profissao profissao2) { this.profissao2 = profissao2; }
    public Profissao getProfissao3() { return profissao3; }
    public void setProfissao3(Profissao profissao3) { this.profissao3 = profissao3; }
}