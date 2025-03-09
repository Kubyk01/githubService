package com.example.error;

import com.example.model.ErrorResponse;
import com.example.exception.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{"
                        + "\"status\": 404, "
                        + "\"message\": \"" + exception.getMessage() + "\""
                        + "}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}


