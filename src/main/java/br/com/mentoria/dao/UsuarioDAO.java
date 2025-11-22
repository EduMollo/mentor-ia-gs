package br.com.mentoria.dao;

import br.com.mentoria.models.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class UsuarioDAO {

    @Inject
    DataSource dataSource;

    public void salvar(Usuario usuario) {
        System.out.println(">>> Tentando salvar usuário: " + usuario.getEmail());

        String sql = "INSERT INTO usuario (primeiro_nome_usuario, sobrenome_usuairo, email_usuario, hash_senha_usuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ID_USUARIO"})) {

            ps.setString(1, usuario.getPrimeiroNome());
            ps.setString(2, usuario.getSobrenome());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getHashSenha());

            System.out.println(">>> Executando Insert...");
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    usuario.setIdUsuario(idGerado);
                    System.out.println(">>> Usuário salvo com ID: " + idGerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("!!! ERRO AO SALVAR NO BANCO !!!");
            e.printStackTrace();
            throw new RuntimeException("Erro técnico ao salvar usuário: " + e.getMessage());
        }
    }

    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT id_usuario, primeiro_nome_usuario, sobrenome_usuairo, email_usuario, hash_senha_usuario FROM usuario WHERE email_usuario = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro SQL ao buscar email: " + e.getMessage());
        }
        return null;
    }}