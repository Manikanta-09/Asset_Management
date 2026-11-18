package com.enterprize.assetmanagement.department.service;

import com.enterprize.assetmanagement.department.dto.DepartmentCreateRequest;
import com.enterprize.assetmanagement.department.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse>  getAllDepartmentsWithAssets();

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse createDepartment(DepartmentCreateRequest request);

    void deleteDepartment(Long id);
}
