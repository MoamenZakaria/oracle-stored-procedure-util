package com.moamen.ora.db.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moamen.ora.db.dto.SpInfo;
import com.moamen.ora.db.service.StoredProcedureService;
import com.moamen.ora.db.dao.OracleStoredProcedureInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@ApplicationScoped
public class StoredProcedureServiceImpl implements StoredProcedureService {
    @Inject
    OracleStoredProcedureInfo oracleStoredProcedureInfo;

    @Override
    public SpInfo getSpInfo(String spName, String packageName, String owner) throws JsonProcessingException {

        final String result = oracleStoredProcedureInfo.fetchSpInfoByName(spName, packageName, owner);
        if (StringUtils.isBlank(result)) {
            throw new RuntimeException("No result found for stored procedure : " + spName);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        SpInfo spMetadata = SpInfo.builder().spName(spName).packageName(packageName).owner(owner).build();
        List<SpInfo.SpParams> spParamDTOs = objectMapper.readValue(result, new TypeReference<List<SpInfo.SpParams>>() {
        });
        spMetadata.setSpParams(spParamDTOs);
        return spMetadata;

    }
}
