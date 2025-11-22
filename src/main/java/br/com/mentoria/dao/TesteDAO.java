package br.com.mentoria.dao;

import br.com.mentoria.models.Teste;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class TesteDAO {
    @Inject DataSource dataSource;

    public void salvar(Teste teste) {
        String sql = "INSERT INTO teste (data_teste, fk_usuario) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id_teste"})) { // Oracle Identity

            ps.setDate(1, java.sql.Date.valueOf(teste.getDataTeste()));
            ps.setInt(2, teste.getUsuario().getIdUsuario());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) teste.setIdTeste(rs.getInt(1));
            }
        } catch (SQLException e) { throw new RuntimeException("Erro ao salvar teste", e); }
    }
}