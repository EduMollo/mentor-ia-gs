package br.com.mentoria.service;

import br.com.mentoria.dao.UsuarioDAO;
import br.com.mentoria.dto.LoginRequestDto;
import br.com.mentoria.dto.LoginResponseDto;
import br.com.mentoria.dto.UsuarioRequestDto;
import br.com.mentoria.models.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.algorithm.SignatureAlgorithm;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    UsuarioDAO usuarioDAO;

    @ConfigProperty(name = "mentoria.jwt.secret")
    String jwtSecret;

    @Transactional
    public void registrarUsuario(UsuarioRequestDto req) {
        if (usuarioDAO.buscarPorEmail(req.email) != null) {
            throw new WebApplicationException("E-mail já cadastrado.", Response.Status.CONFLICT);
        }

        String senhaHash = BcryptUtil.bcryptHash(req.senha);
        Usuario novoUsuario = new Usuario(req.primeiroNome, req.sobrenome, req.email, senhaHash);
        usuarioDAO.salvar(novoUsuario);
    }

    public LoginResponseDto autenticar(LoginRequestDto req) {
        try {
            Usuario u = usuarioDAO.buscarPorEmail(req.email);

            if (u == null || !BcryptUtil.matches(req.senha, u.getHashSenha())) {
                throw new WebApplicationException("Credenciais inválidas", Response.Status.UNAUTHORIZED);
            }

            SecretKey secretKey = new SecretKeySpec(
                    jwtSecret.getBytes(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.getAlgorithm()
            );

            String token = Jwt.issuer("https://mentoria.com.br/api")
                    .upn(u.getEmail())
                    .groups(Set.of("USER"))
                    .claim("id_usuario", u.getIdUsuario())
                    .claim("nome_completo", u.getPrimeiroNome() + " " + u.getSobrenome())
                    .expiresIn(3600)
                    .jws()
                    .algorithm(SignatureAlgorithm.HS256)
                    .sign(secretKey);

            return new LoginResponseDto(token, u.getPrimeiroNome(), u.getEmail());

        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof WebApplicationException) throw (WebApplicationException) e;
            throw new WebApplicationException("Erro ao gerar token: " + e.getMessage(), 500);
        }
    }
}