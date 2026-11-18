package com.enterprize.assetmanagement.asset.dto;


import com.enterprize.assetmanagement.asset.model.AssetStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetFilterCriteria {
    private String name;
    private String serialNumber;
    private String assetType;
    private AssetStatus status;
}
