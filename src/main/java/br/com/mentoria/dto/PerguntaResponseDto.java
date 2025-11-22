package br.com.mentoria.dto;

public class PerguntaResponseDto {
    public int id;
    public String texto;
    public String categoriaHabilidade;
    public String descricaoHabilidade;

    public PerguntaResponseDto(int id, String texto, String categoriaHabilidade, String descricaoHabilidade) {
        this.id = id;
        this.texto = texto;
        this.categoriaHabilidade = categoriaHabilidade;
        this.descricaoHabilidade = descricaoHabilidade;
    }
}