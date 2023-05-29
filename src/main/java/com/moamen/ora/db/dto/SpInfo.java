package com.moamen.ora.db.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpInfo {

    String spName;
    String packageName;
    String owner;

    List<SpParams> spParams;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SpParams {

        Integer index;
        String spName;
        String paramName;
        String paramType;
        String value;
        String javaType;
        String sqlType;
    }


}
