package com.moamen.ora.db.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moamen.ora.db.dto.SpInfo;
import com.moamen.ora.db.service.StoredProcedureService;
import com.moamen.ora.db.dao.OracleStoredProcedureInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.ObjectUtils;


@ApplicationScoped
public class StoredProcedureServiceImpl implements StoredProcedureService {
    @Inject
    OracleStoredProcedureInfo oracleStoredProcedureInfo;

    @Override
    public SpInfo getSpInfo(String spName, String packageName, String owner)  {

        final SpInfo result = oracleStoredProcedureInfo.fetchSpInfoByName(spName, packageName, owner);
        if (ObjectUtils.isEmpty(result)) {
            throw new RuntimeException("Error: No stored procedure matching the search criteria was found, or the found stored procedure '" + spName + "' does not have any IN or OUT parameters.");
        }
        return result;

    }
}
