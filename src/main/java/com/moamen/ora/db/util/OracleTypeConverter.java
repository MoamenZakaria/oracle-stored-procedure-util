package com.moamen.ora.db.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.sql.Blob;

@UtilityClass
public class OracleTypeConverter {
    public static Class<?> convertToJavaType(String oracleType) {
        return switch (oracleType.toUpperCase()) {
            case "VARCHAR2", "CHAR", "NCHAR", "NVARCHAR2", "CLOB" -> String.class;
            case "DATE", "TIMESTAMP" -> java.sql.Timestamp.class;
            case "FLOAT", "NUMBER", "INTEGER", "PLS_INTEGER" -> BigDecimal.class;
            case "BLOB" -> Blob.class;

            default -> throw new IllegalArgumentException("Unsupported Oracle type: " + oracleType);
        };
    }
}
