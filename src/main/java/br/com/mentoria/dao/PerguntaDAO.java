package br.com.mentoria.dao;

import br.com.mentoria.models.Habilidade;
import br.com.mentoria.models.Pergunta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PerguntaDAO {

    @Inject DataSource dataSource;

    public List<Pergunta> listarTodas() {
        List<Pergunta> lista = new ArrayList<>();
        String sql = "SELECT p.id_pergunta, p.texto_pergunta, h.id_habilidade, h.nome_habilidade, h.descricao_habilidade " +
                "FROM pergunta p " +
                "JOIN habilidade h ON p.fk_habilidade = h.id_habilidade " +
                "ORDER BY h.id_habilidade, p.id_pergunta";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Habilidade h = new Habilidade(
                        rs.getInt("id_habilidade"),
                        rs.getString("nome_habilidade"),
                        rs.getString("descricao_habilidade")
                );
                lista.add(new Pergunta(rs.getInt("id_pergunta"), rs.getString("texto_pergunta"), h));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar perguntas", e);
        }
        return lista;
    }

    public Pergunta buscarPorId(int id) {
        String sql = "SELECT p.id_pergunta, p.texto_pergunta, h.id_habilidade, h.nome_habilidade, h.descricao_habilidade " +
                "FROM pergunta p JOIN habilidade h ON p.fk_habilidade = h.id_habilidade WHERE p.id_pergunta = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Habilidade h = new Habilidade(
                            rs.getInt("id_habilidade"),
                            rs.getString("nome_habilidade"),
                            rs.getString("descricao_habilidade")
                    );
                    return new Pergunta(rs.getInt("id_pergunta"), rs.getString("texto_pergunta"), h);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }
}