package com.enterprize.assetmanagement.employee.repository;

import com.enterprize.assetmanagement.employee.model.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee , Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<Employee> findByEmailIgnoreCase(String email);

    @EntityGraph
    List<Employee> findByDepartmentId(Long departmentId);

    @Override
    @EntityGraph(attributePaths = {"department"})
    List<Employee> findAll();
}
