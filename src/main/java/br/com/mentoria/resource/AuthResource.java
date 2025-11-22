package br.com.mentoria.resource;

import br.com.mentoria.dto.LoginRequestDto;
import br.com.mentoria.dto.LoginResponseDto;
import br.com.mentoria.dto.UsuarioRequestDto;
import br.com.mentoria.service.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    public Response register(@Valid UsuarioRequestDto req) {
        authService.registrarUsuario(req);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequestDto req) {
        LoginResponseDto response = authService.autenticar(req);
        return Response.ok(response).build();
    }
}