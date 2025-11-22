package br.com.mentoria.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CalculadoraDAO {
    @Inject DataSource dataSource;

    public void calcularScores(int idTeste) {
        String sql = "INSERT INTO score_teste_habilidade (fk_teste, fk_habilidade, score) " +
                "SELECT r.fk_teste, p.fk_habilidade, SUM(r.valor_resposta) " +
                "FROM resposta r " +
                "JOIN pergunta p ON r.fk_pergunta = p.id_pergunta " +
                "WHERE r.fk_teste = ? " +
                "GROUP BY r.fk_teste, p.fk_habilidade";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTeste);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao calcular scores", e); }
    }

    public void gerarResultadoFinal(int idTeste) {
        String sqlRanking = "SELECT p.id_profissao, SUM(s.score) as afinidade " +
                "FROM score_teste_habilidade s " +
                "JOIN profissao_habilidade ph ON s.fk_habilidade = ph.fk_habilidade " +
                "JOIN profissao p ON ph.fk_profissao = p.id_profissao " +
                "WHERE s.fk_teste = ? " +
                "GROUP BY p.id_profissao " +
                "ORDER BY afinidade DESC " +
                "FETCH FIRST 3 ROWS ONLY";

        List<Integer> topProfissoes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlRanking)) {
            ps.setInt(1, idTeste);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) topProfissoes.add(rs.getInt("id_profissao"));
            }
            if (!topProfissoes.isEmpty()) salvarResultado(conn, idTeste, topProfissoes);
        } catch (SQLException e) { throw new RuntimeException("Erro ao gerar resultado final", e); }
    }

    private void salvarResultado(Connection conn, int idTeste, List<Integer> profissoes) throws SQLException {
        String sqlInsert = "INSERT INTO resultado (fk_teste, fk_profissao_recomendada_1, fk_profissao_recomendada_2, fk_profissao_recomendada_3) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setInt(1, idTeste);
            ps.setObject(2, profissoes.size() > 0 ? profissoes.get(0) : null);
            ps.setObject(3, profissoes.size() > 1 ? profissoes.get(1) : null);
            ps.setObject(4, profissoes.size() > 2 ? profissoes.get(2) : null);
            ps.executeUpdate();
        }
    }
}