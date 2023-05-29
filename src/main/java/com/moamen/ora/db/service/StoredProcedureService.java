package com.moamen.ora.db.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moamen.ora.db.dto.SpInfo;

public interface StoredProcedureService {
    /**
     *  Retrieves information about a stored procedure.
     * @param spName The name of the stored procedure.
     * @param packageName The name of the package containing the stored procedure (optional).
     * @param owner The owner of the stored procedure (optional).
     * @return A SpInfo containing information about the stored procedure.
     * @throws JsonProcessingException If an error occurs while processing the response.
     */
    SpInfo getSpInfo(String spName, String packageName, String owner) throws JsonProcessingException;
}
