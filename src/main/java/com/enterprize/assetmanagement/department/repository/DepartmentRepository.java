package com.enterprize.assetmanagement.department.repository;

import com.enterprize.assetmanagement.department.model.Department;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByCodeIgnoreCase(String code);

    // 1. Solution A: JPQL with JOIN FETCH
    // Forces an SQL INNER/LEFT JOIN to fetch Department and Assets in exactly 1 query
    @Query("SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.assets")
    List<Department> findAllWithAssetsJoinFetch();

    // 2. Solution B: Spring Data @EntityGraph
    // Achieves the exact same single-query join declaratively without writing raw JPQL
    @EntityGraph(attributePaths = {"assets"})
    @Query("SELECT d FROM Department d WHERE d.id = :id")
    Optional<Department> findByIdWithAssetsGraph(@Param("id") Long id);
}
