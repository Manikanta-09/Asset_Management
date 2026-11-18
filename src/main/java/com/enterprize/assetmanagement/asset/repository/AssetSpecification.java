package com.enterprize.assetmanagement.asset.repository;

import com.enterprize.assetmanagement.asset.dto.AssetFilterCriteria;
import com.enterprize.assetmanagement.asset.model.Asset;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public class AssetSpecification {
    private AssetSpecification(){

    }
    public static Specification<Asset> withCriteria(AssetFilterCriteria criteria){
        return (root,query,cb)->{
            if(criteria == null){
                return cb.conjunction();
            }
            List<Predicate> predicate = new ArrayList<>();
            if(criteria.getName() !=null && !criteria.getName().isBlank()){
                predicate.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + criteria.getName().trim().toLowerCase() + "%"
                        ));
            }
            if(criteria.getSerialNumber()!=null && !criteria.getSerialNumber().isBlank()){
                predicate.add(cb.like(
                   cb.lower(root.get("serialNumber")),
                   "%" + criteria.getSerialNumber().trim().toLowerCase()
                ));
            }
            if(criteria.getAssetType()!=null && !criteria.getAssetType().isBlank()){
                predicate.add(cb.equal(
                        cb.upper(root.get("assetType")),
                        criteria.getAssetType().trim().toUpperCase()
                ));
            }
            if(criteria.getStatus()!=null){
                predicate.add(cb.equal(root.get("status"),criteria.getStatus()));
            }
            return cb.and (predicate.toArray(new Predicate[0]));
        };
    }
}
