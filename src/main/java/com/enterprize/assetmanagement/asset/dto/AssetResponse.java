package com.enterprize.assetmanagement.asset.dto;

import com.enterprize.assetmanagement.asset.model.AssetStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AssetResponse {
    private Long id;
    private String name;
    private String serialNumber;
    private String assetType;
    private AssetStatus status;
    private Instant createdAt;
}
