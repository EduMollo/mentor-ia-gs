package br.com.mentoria.dto;

import java.time.LocalDate;

public class ResultadoResponseDto {
    public int idTeste;
    public LocalDate dataRealizacao;
    public String nomeCandidato;

    public ProfissaoResponseDto recomendacaoPrimaria;
    public ProfissaoResponseDto recomendacaoSecundaria;
    public ProfissaoResponseDto recomendacaoTerciaria;

    public ResultadoResponseDto() {}
    public ResultadoResponseDto(int idTeste, LocalDate dataRealizacao, String nomeCandidato,
                                ProfissaoResponseDto p1, ProfissaoResponseDto p2, ProfissaoResponseDto p3) {
        this.idTeste = idTeste;
        this.dataRealizacao = dataRealizacao;
        this.nomeCandidato = nomeCandidato;
        this.recomendacaoPrimaria = p1;
        this.recomendacaoSecundaria = p2;
        this.recomendacaoTerciaria = p3;
    }
}