package br.com.mentoria.service;

import br.com.mentoria.dao.*;
import br.com.mentoria.dto.*;
import br.com.mentoria.models.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ForbiddenException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionarioService {

    @Inject PerguntaDAO perguntaDAO;
    @Inject TesteDAO testeDAO;
    @Inject RespostaDAO respostaDAO;
    @Inject UsuarioDAO usuarioDAO;
    @Inject CalculadoraDAO calculadoraDAO;
    @Inject ResultadoDAO resultadoDAO;

    public List<PerguntaResponseDto> buscarPerguntas() {
        return perguntaDAO.listarTodas().stream()
                .map(p -> new PerguntaResponseDto(
                        p.getIdPergunta(),
                        p.getTextoPergunta(),
                        p.getHabilidade().getNomeHabilidade(),
                        p.getHabilidade().getDescricaoHabilidade()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public int processarSubmissao(String emailUsuario, SubmissaoTesteRequestDto submissao) {
        Usuario usuario = usuarioDAO.buscarPorEmail(emailUsuario);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        Teste teste = new Teste();
        teste.setDataTeste(LocalDate.now());
        teste.setUsuario(usuario);
        testeDAO.salvar(teste);

        for (RespostaRequestDto item : submissao.respostas) {
            Pergunta p = perguntaDAO.buscarPorId(item.idPergunta);
            if (p != null) {
                Resposta r = new Resposta();
                r.setTeste(teste);
                r.setPergunta(p);
                r.setValorResposta(item.nota);
                respostaDAO.salvar(r);
            }
        }

        calculadoraDAO.calcularScores(teste.getIdTeste());
        calculadoraDAO.gerarResultadoFinal(teste.getIdTeste());

        return teste.getIdTeste();
    }

    public ResultadoResponseDto buscarResultado(int idTeste, String emailUsuario) {
        Resultado resultado = resultadoDAO.buscarPorIdTeste(idTeste);

        if (resultado == null) {
            return null;
        }

        if (!resultado.getTeste().getUsuario().getEmail().equals(emailUsuario)) {
            throw new ForbiddenException("Você não tem permissão para visualizar este resultado.");
        }

        return new ResultadoResponseDto(
                resultado.getTeste().getIdTeste(),
                resultado.getTeste().getDataTeste(),
                resultado.getTeste().getUsuario().getPrimeiroNome(),
                mapearProfissao(resultado.getProfissao1()),
                mapearProfissao(resultado.getProfissao2()),
                mapearProfissao(resultado.getProfissao3())
        );
    }

    private ProfissaoResponseDto mapearProfissao(Profissao p) {
        if (p == null) return null;
        return new ProfissaoResponseDto(p.getIdProfissao(), p.getNomeProfissao());
    }
}