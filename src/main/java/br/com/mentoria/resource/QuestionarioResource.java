package br.com.mentoria.resource;

import br.com.mentoria.dto.PerguntaResponseDto;
import br.com.mentoria.dto.ResultadoResponseDto;
import br.com.mentoria.dto.SubmissaoTesteRequestDto;
import br.com.mentoria.service.QuestionarioService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.annotation.security.PermitAll;
import java.util.List;

@Path("/api/questionario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestionarioResource {

    @Inject
    QuestionarioService service;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/perguntas")
    @PermitAll
    public Response getPerguntas() {
        List<PerguntaResponseDto> perguntas = service.buscarPerguntas();
        return Response.ok(perguntas).build();
    }

    @POST
    @Path("/responder")
    @Authenticated
    public Response responderQuestionario(@Valid SubmissaoTesteRequestDto body) {
        String emailUsuario = jwt.getName();
        int idTesteGerado = service.processarSubmissao(emailUsuario, body);

        return Response.status(Response.Status.CREATED)
                .entity("{\"idTeste\": " + idTesteGerado + "}")
                .build();
    }

    @GET
    @Path("/resultado/{idTeste}")
    @Authenticated
    public Response obterResultado(@PathParam("idTeste") int idTeste) {
        String emailUsuario = jwt.getName();
        ResultadoResponseDto resultado = service.buscarResultado(idTeste, emailUsuario);

        if (resultado == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Resultado não encontrado.")
                    .build();
        }
        return Response.ok(resultado).build();
    }
}
