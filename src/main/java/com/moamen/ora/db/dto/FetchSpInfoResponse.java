package com.moamen.ora.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FetchSpInfoResponse {
    String spName;
    String packageName;
    String owner;
    List<SpParam> spInParams;
    List<SpParam> spOutParams;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SpParam {
        Integer index;
        String name;
        String value;
        String javaType;
        String sqlType;
    }
}
