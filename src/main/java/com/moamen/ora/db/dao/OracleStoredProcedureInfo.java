package com.moamen.ora.db.dao;

import com.moamen.ora.db.dto.SpInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@ApplicationScoped
public class OracleStoredProcedureInfo {

    private final EntityManager entityManager;

    public OracleStoredProcedureInfo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * fetch stored procedure meta-data and returns the result as a JSON array.
     *
     * @param spName      The name of the stored procedure.
     * @param packageName The name of the package containing the stored procedure (optional).
     * @param owner       The owner of the stored procedure (optional).
     * @return SpInfo     Return stored procedure or NULL if not matching.
     */
    public SpInfo fetchSpInfoByName(String spName, String packageName, String owner) {
        // Build the queryStr to fetch the stored procedure arguments.
        final String queryStr = buildQuery(packageName, owner);
        // Create the native queryStr and set parameters.
        final Query nativeQuery = entityManager.createNativeQuery(queryStr);
        setApplicableParams(spName, packageName, owner, nativeQuery);
        // Execute the query and return the result.
        List<Object[]> results = nativeQuery.getResultList();
        return mapResultSetToSpInfo(spName, packageName, owner, results);
    }

    private String buildQuery(String packageName, String owner) {
        final StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(""" 
                SELECT POSITION,OBJECT_NAME,ARGUMENT_NAME ,IN_OUT,DATA_TYPE
                FROM all_arguments
                WHERE object_name = :spName \n
                """);
        // Add package name filter if provided.
        if (StringUtils.isNotBlank(packageName)) {
            queryBuilder.append("AND PACKAGE_NAME = :packageName \n");
        }
        // Add owner filter if provided.
        if (StringUtils.isNotBlank(owner)) {
            queryBuilder.append("AND owner = :owner \n");
        }
        queryBuilder.append("ORDER BY POSITION");
        return queryBuilder.toString();
    }

    private void setApplicableParams(String spName, String packageName, String owner, Query nativeQuery) {
        nativeQuery.setParameter("spName", spName.toUpperCase());
        if (StringUtils.isNotBlank(packageName)) {
            nativeQuery.setParameter("packageName", packageName.toUpperCase());
        }
        if (StringUtils.isNotBlank(owner)) {
            nativeQuery.setParameter("owner", owner.toUpperCase());
        }
    }

    private SpInfo mapResultSetToSpInfo(String spName, String packageName, String owner, List<Object[]> resultSet) {
        if (Objects.isNull(resultSet) || resultSet.isEmpty()) {
            return null;
        }
        return SpInfo.builder()
                .packageName(packageName)
                .spName(spName)
                .owner(owner)
                .spParams(resultSet.stream().map(spParamMapper).toList())
                .build();
    }

    private final Function<Object[], SpInfo.SpParams> spParamMapper = row -> {
        SpInfo.SpParams dto = new SpInfo.SpParams();
        dto.setIndex(((Float) row[0]).intValue());
        dto.setSpName((String) row[1]);
        dto.setParamName((String) row[2]);
        dto.setParamType((String) row[3]);
        dto.setSqlType((String) row[4]);
        return dto;
    };

}
