package com.moamen.ora.db.exceptions;

import com.moamen.ora.db.dto.ErrorResponse;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;


@Provider
@Priority(Integer.MIN_VALUE)
public class GlobalExceptionsHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("0", "Oops! Technical glitch!", Collections.singletonList(throwable.getMessage())))
                .build();
    }
}

