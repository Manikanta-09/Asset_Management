package com.enterprize.assetmanagement.employee.service;

import com.enterprize.assetmanagement.employee.dto.EmployeeCreateRequest;
import com.enterprize.assetmanagement.employee.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeCreateRequest request);
    EmployeeResponse getEmployeeById(Long id);
    List<EmployeeResponse> getAllEmployees();
    List<EmployeeResponse> getEmployeesByDepartment(Long DepartmentId);
}
