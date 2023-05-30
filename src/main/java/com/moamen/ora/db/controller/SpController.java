package com.moamen.ora.db.controller;

import com.moamen.ora.db.dto.FetchSpInfoResponse;
import com.moamen.ora.db.service.StoredProcedureService;
import com.moamen.ora.db.util.SpInfoMapper;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/sp")
public class SpController {

    @Inject
    StoredProcedureService spService;
    @Inject
    SpInfoMapper spInfoMapper;


    @Operation(summary = "Retrieves information about a stored procedure.")
    @APIResponse(responseCode = "200", description = "The stored procedure information retrieved successfully.")
    @APIResponse(responseCode = "400", description = "The stored procedure information not found.")
    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public FetchSpInfoResponse getSpInfo(@RestQuery @NotBlank(message = "spName is mandatory") String spName,
                                         @RestQuery String packageName,
                                         @RestQuery String owner) {
        // Map the stored procedure information to the response object if it is not null
        return spInfoMapper.mapSpInfoToResponse(spService.getSpInfo(spName, packageName, owner));
    }


}

