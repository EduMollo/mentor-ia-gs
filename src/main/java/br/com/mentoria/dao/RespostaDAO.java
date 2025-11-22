package br.com.mentoria.dao;

import br.com.mentoria.models.Resposta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class RespostaDAO {
    @Inject DataSource dataSource;

    public void salvar(Resposta resposta) {
        String sql = "INSERT INTO resposta (fk_teste, fk_pergunta, valor_resposta) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, resposta.getTeste().getIdTeste());
            ps.setInt(2, resposta.getPergunta().getIdPergunta());
            ps.setInt(3, resposta.getValorResposta());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao salvar resposta", e); }
    }
}