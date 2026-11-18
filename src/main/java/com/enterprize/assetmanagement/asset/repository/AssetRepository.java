package com.enterprize.assetmanagement.asset.repository;

import com.enterprize.assetmanagement.asset.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset,Long>, JpaSpecificationExecutor<Asset> {
    boolean existsBySerialNumberIgnoreCase(String serialNumber);
    Optional<Asset>  findBySerialNumber(String serailNumber);

}
