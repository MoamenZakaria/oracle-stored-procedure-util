package com.moamen.ora.db.exceptions;

import com.moamen.ora.db.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Response.ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
        builder.entity(buildErrorResponse(exception));
        return builder.build();
    }

    private ErrorResponse buildErrorResponse(ConstraintViolationException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode("0");
        errorResponse.setMessage("Constraint Violation");
        errorResponse.setErrors(exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList());
        return errorResponse;
    }
}
