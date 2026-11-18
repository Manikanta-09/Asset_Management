package com.enterprize.assetmanagement.department.dto;

import com.enterprize.assetmanagement.asset.dto.AssetResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class DepartmentResponse {
    private Long id;
    private String name;
    private String code;
    private int assetCount;
    private List<AssetResponse> assets;
}
