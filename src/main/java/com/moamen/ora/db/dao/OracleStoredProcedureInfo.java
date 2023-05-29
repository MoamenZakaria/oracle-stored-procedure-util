package com.moamen.ora.db.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@ApplicationScoped
public class OracleStoredProcedureInfo {

    @Inject
    EntityManager entityManager;

    /**
     * fetch stored procedure meta-data and returns the result as a JSON array.
     *
     * @param spName      The name of the stored procedure.
     * @param packageName The name of the package containing the stored procedure (optional).
     * @param owner       The owner of the stored procedure (optional).
     * @return String The result of the stored procedure as a JSON array.
     */
    @Transactional
    public String fetchSpInfoByName(String spName, String packageName, String owner) {
        // Build the query to fetch the stored procedure arguments.
        final StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("""
                SELECT JSON_ARRAYAGG(
                           JSON_OBJECT(
                               'index' VALUE sequence,
                               'spName' VALUE object_name,
                               'paramName' VALUE argument_name,
                               'paramType' VALUE in_out,
                               'value' VALUE 'placeholder',
                               'javaType' VALUE CASE
                                            WHEN data_type IN ('VARCHAR', 'VARCHAR2', 'NVARCHAR', 'NVARCHAR2', 'CHAR', 'NCHAR', 'CLOB')
                                              THEN 'String'
                                            WHEN data_type IN ('NUMBER', 'FLOAT')
                                              THEN 'Integer'
                                            WHEN data_type IN ('DATE', 'TIMESTAMP')
                                              THEN 'Date'
                                            ELSE 'Object'
                                          END,
                               'sqlType' VALUE data_type
                           )
                       ) as result
                FROM all_arguments
                WHERE object_name = :spName
                """);
        // Add package name filter if provided.
        if (StringUtils.isNotBlank(packageName)) {
            queryBuilder.append("AND PACKAGE_NAME = :packageName \n");
        }
        // Add owner filter if provided.
        if (StringUtils.isNotBlank(owner)) {
            queryBuilder.append("AND owner = :owner \n");
        }

        // Finalize the query string.
        final String query = queryBuilder.toString();
        // Create the native query and set parameters.
        final Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("spName", spName.toUpperCase());
        if (StringUtils.isNotBlank(packageName)) {
            nativeQuery.setParameter("packageName", packageName.toUpperCase());
        }
        if (StringUtils.isNotBlank(owner)) {
            nativeQuery.setParameter("owner", owner.toUpperCase());
        }
        // Execute the query and return the result.
        return (String) nativeQuery.getSingleResult();
    }
}
