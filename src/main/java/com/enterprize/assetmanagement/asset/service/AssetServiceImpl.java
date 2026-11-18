package com.enterprize.assetmanagement.asset.service;

import com.enterprize.assetmanagement.asset.dto.AssetCreateRequest;
import com.enterprize.assetmanagement.asset.dto.AssetFilterCriteria;
import com.enterprize.assetmanagement.asset.dto.AssetResponse;
import com.enterprize.assetmanagement.asset.dto.PagedResponse;
import com.enterprize.assetmanagement.asset.model.Asset;
import com.enterprize.assetmanagement.asset.model.AssetStatus;
import com.enterprize.assetmanagement.asset.repository.AssetRepository;
import com.enterprize.assetmanagement.asset.repository.AssetSpecification;
import com.enterprize.assetmanagement.common.exception.DuplicateResourceException;
import com.enterprize.assetmanagement.common.exception.ResourceNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;
    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }
    @Override
    @Transactional
    public AssetResponse createAsset(AssetCreateRequest request) {
        if (assetRepository.existsBySerialNumberIgnoreCase(request.getSerialNumber().trim())) {
            throw new DuplicateResourceException("Asset already exists with serial number" + request.getSerialNumber());
        }
        Asset asset = Asset.builder()
                .serialNumber(request.getSerialNumber())
                .assetType(request.getAssetType())
                .name(request.getName())
                .status(AssetStatus.AVAILABLE)
                .build();
        Asset savedAsset = assetRepository.save(asset);
        return mapToResponse(savedAsset);
    }

    @Override
    public AssetResponse getAssetById(Long id) {
        Asset asset = assetRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Asset not found with ID: " + id));
        return mapToResponse(asset);
    }
    @Override
    public PagedResponse<AssetResponse> getAssets(AssetFilterCriteria criteria, Pageable pageable) {
        Specification<Asset> spec = AssetSpecification.withCriteria(criteria);
        Page<Asset> assetPage = assetRepository.findAll(spec,pageable);
        Page<AssetResponse> responsePage = assetPage.map(this::mapToResponse);
        return PagedResponse.of(responsePage);
    }
    @Override
    @Transactional
    public void deleteAsset(Long id) {
//        if (!assetRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Cannot delete with this " + id);
//        }
//        assetRepository.deleteById(id);
//
        Asset asset = assetRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Asset not found with id "+id));
        assetRepository.delete(asset);
    }

    public AssetResponse mapToResponse(Asset asset) {
        return AssetResponse.builder()
                .id(asset.getId())
                .name(asset.getName())
                .serialNumber(asset.getSerialNumber())
                .status(asset.getStatus())
                .assetType(asset.getAssetType())
                .createdAt(asset.getCreatedAt())
                .build();

    }
}
