package br.com.mentoria.dao;

import br.com.mentoria.models.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class ResultadoDAO {

    @Inject
    DataSource dataSource;

    public Resultado buscarPorIdTeste(int idTeste) {
        String sql =
                "SELECT " +
                        "  r.id_resultado, " +
                        "  t.id_teste, t.data_teste, " +
                        "  u.id_usuario, u.primeiro_nome_usuario, u.email_usuario, " +
                        "  p1.id_profissao as id_p1, p1.nome_profissao as nome_p1, " +
                        "  p2.id_profissao as id_p2, p2.nome_profissao as nome_p2, " +
                        "  p3.id_profissao as id_p3, p3.nome_profissao as nome_p3 " +
                        "FROM resultado r " +
                        "JOIN teste t ON r.fk_teste = t.id_teste " +
                        "JOIN usuario u ON t.fk_usuario = u.id_usuario " +
                        "LEFT JOIN profissao p1 ON r.fk_profissao_recomendada_1 = p1.id_profissao " +
                        "LEFT JOIN profissao p2 ON r.fk_profissao_recomendada_2 = p2.id_profissao " +
                        "LEFT JOIN profissao p3 ON r.fk_profissao_recomendada_3 = p3.id_profissao " +
                        "WHERE r.fk_teste = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTeste);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Resultado resultado = new Resultado();
                    resultado.setIdResultado(rs.getInt("id_resultado"));

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setPrimeiroNome(rs.getString("primeiro_nome_usuario"));
                    usuario.setEmail(rs.getString("email_usuario"));

                    Teste teste = new Teste();
                    teste.setIdTeste(rs.getInt("id_teste"));
                    teste.setDataTeste(rs.getDate("data_teste").toLocalDate());
                    teste.setUsuario(usuario);
                    resultado.setTeste(teste);

                    if (rs.getObject("id_p1") != null)
                        resultado.setProfissao1(new Profissao(rs.getInt("id_p1"), rs.getString("nome_p1")));

                    if (rs.getObject("id_p2") != null)
                        resultado.setProfissao2(new Profissao(rs.getInt("id_p2"), rs.getString("nome_p2")));

                    if (rs.getObject("id_p3") != null)
                        resultado.setProfissao3(new Profissao(rs.getInt("id_p3"), rs.getString("nome_p3")));

                    return resultado;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar resultado por ID do teste", e);
        }
        return null;
    }
}