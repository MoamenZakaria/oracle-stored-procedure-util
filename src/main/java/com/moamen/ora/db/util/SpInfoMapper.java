package com.moamen.ora.db.util;

import com.moamen.ora.db.dto.FetchSpInfoResponse;
import com.moamen.ora.db.dto.SpInfo;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class SpInfoMapper {


    public FetchSpInfoResponse mapSpInfoToResponse(SpInfo spInfo) {
        List<FetchSpInfoResponse.SpParam> spInParams = new ArrayList<>();
        List<FetchSpInfoResponse.SpParam> spOutParams = new ArrayList<>();

        spInfo.getSpParams().forEach(spParam -> {
            FetchSpInfoResponse.SpParam mappedParam = FetchSpInfoResponse.SpParam.builder()
                    .index(spParam.getIndex())
                    .name(spParam.getParamName())
                    .value(spParam.getValue())
                    .javaType(spParam.getJavaType())
                    .sqlType(spParam.getSqlType())
                    .build();

            if (spParam.getParamType().equals("IN")) {
                spInParams.add(mappedParam);
            } else if (spParam.getParamType().equals("OUT")) {
                spOutParams.add(mappedParam);
            }
        });
        spInParams.sort(Comparator.comparingInt(FetchSpInfoResponse.SpParam::getIndex));
        spOutParams.sort(Comparator.comparingInt(FetchSpInfoResponse.SpParam::getIndex));
        return FetchSpInfoResponse.builder()
                .spName(spInfo.getSpName())
                .packageName(spInfo.getPackageName())
                .owner(spInfo.getOwner())
                .spInParams(spInParams)
                .spOutParams(spOutParams)
                .build();
    }


}
