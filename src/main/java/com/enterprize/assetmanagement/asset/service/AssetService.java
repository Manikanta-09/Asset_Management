package com.enterprize.assetmanagement.asset.service;

import com.enterprize.assetmanagement.asset.dto.AssetCreateRequest;
import com.enterprize.assetmanagement.asset.dto.AssetFilterCriteria;
import com.enterprize.assetmanagement.asset.dto.AssetResponse;
import com.enterprize.assetmanagement.asset.dto.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetService {
    AssetResponse createAsset(AssetCreateRequest request);
    AssetResponse getAssetById(Long id);

    PagedResponse<AssetResponse> getAssets(AssetFilterCriteria criteria, Pageable pageable);

    void deleteAsset(Long id);
}