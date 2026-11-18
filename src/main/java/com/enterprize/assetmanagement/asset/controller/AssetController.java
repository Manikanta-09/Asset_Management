package com.enterprize.assetmanagement.asset.controller;

import com.enterprize.assetmanagement.asset.dto.AssetCreateRequest;
import com.enterprize.assetmanagement.asset.dto.AssetFilterCriteria;
import com.enterprize.assetmanagement.asset.dto.AssetResponse;
import com.enterprize.assetmanagement.asset.dto.PagedResponse;
import com.enterprize.assetmanagement.asset.service.AssetService;
import com.enterprize.assetmanagement.asset.model.AssetStatus;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/asset")
public class AssetController {

    private final AssetService assetService;
    public AssetController(AssetService assetService){
        this.assetService = assetService;
    }
    @PostMapping
    public ResponseEntity<AssetResponse> createAsset(@Valid @RequestBody AssetCreateRequest request){
        AssetResponse response = assetService.createAsset(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }
    @GetMapping
    public ResponseEntity<PagedResponse<AssetResponse>> getAssets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) String assetType,
            @RequestParam(required = false) AssetStatus status,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        AssetFilterCriteria criteria = AssetFilterCriteria.builder()
                .name(name)
                .serialNumber(serialNumber)
                .assetType(assetType)
                .status(status)
                .build();

        return ResponseEntity.ok(assetService.getAssets(criteria, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id){
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }
}
