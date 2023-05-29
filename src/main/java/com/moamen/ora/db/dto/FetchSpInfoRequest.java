package com.moamen.ora.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FetchSpInfoRequest {
    String spName;
    String packageName;
    String owner;
}
