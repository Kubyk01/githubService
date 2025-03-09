package com.example.resource;

import com.example.model.ErrorResponse;
import com.example.model.RepositoryResponse;
import com.example.service.GitHubService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;

import java.util.List;

@Path("/github")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GitHubResource {

    @Inject
    GitHubService gitHubService;

    @GET
    @Path("/{username}/repos")
    public Uni<?> getRepositories(@PathParam("username") String username) {
        return gitHubService.getUserRepositories(username)
                .onItem().transform(repos -> Response.ok(repos).build())
                .onFailure(NotFoundException.class)
                .recoverWithItem(() -> Response.status(404).entity(new ErrorResponse(404, "User not found")).build());
    }
}
