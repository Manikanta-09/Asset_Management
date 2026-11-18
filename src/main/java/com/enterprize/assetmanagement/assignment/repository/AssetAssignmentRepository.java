package com.enterprize.assetmanagement.assignment.repository;

import com.enterprize.assetmanagement.assignment.model.AssetAssignment;
import com.enterprize.assetmanagement.assignment.model.AssignmentStatus;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AssetAssignmentRepository extends JpaRepository<AssetAssignment,Long> {
    boolean existsByAssetId(Long assetId);
    @EntityGraph(attributePaths = {"asset" , "employee"})
    Optional<AssetAssignment> findByAssetIdAndStatus(Long assetId, AssignmentStatus status);

    @EntityGraph(attributePaths = {"asset" , "employee"})
    List<AssetAssignment> findByAssetIdOrderByAssignedAtDesc(Long assetId);

    @EntityGraph(attributePaths = {"asset" , "employee"})
    List<AssetAssignment> findByEmployeeIdOrderByAssignedAtDesc(Long employeeId);
}
