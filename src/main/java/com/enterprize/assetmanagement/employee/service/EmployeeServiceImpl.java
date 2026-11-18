package com.enterprize.assetmanagement.employee.service;

import com.enterprize.assetmanagement.common.exception.DuplicateResourceException;
import com.enterprize.assetmanagement.common.exception.ResourceNotFoundException;
import com.enterprize.assetmanagement.department.model.Department;
import com.enterprize.assetmanagement.department.repository.DepartmentRepository;
import com.enterprize.assetmanagement.employee.dto.EmployeeCreateRequest;
import com.enterprize.assetmanagement.employee.dto.EmployeeResponse;
import com.enterprize.assetmanagement.employee.model.Employee;
import com.enterprize.assetmanagement.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository ,DepartmentRepository departmentRepository){
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }
    @Transactional(readOnly = false)
    public EmployeeResponse createEmployee(EmployeeCreateRequest request){
        if(employeeRepository.existsByEmailIgnoreCase(request.getEmail().trim())){
            throw new DuplicateResourceException("Employee with email "+request.getEmail());
        }
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(
                ()->new ResourceNotFoundException("Department not found with Id "+request .getDepartmentId()));

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .department(department)
                .build();

        Employee saved = employeeRepository.save(employee);
        return mapToResponse(saved);
    }

    public EmployeeResponse getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found wit Id "+ id));
        return mapToResponse(employee);

    }

    public List<EmployeeResponse> getAllEmployees(){
        return employeeRepository.findAll().stream()
                .map(this::mapToResponse).toList();
    }

    public List<EmployeeResponse> getEmployeesByDepartment(Long departmentId){
        if(!departmentRepository.existsById(departmentId)){
            throw  new ResourceNotFoundException("Department not found with id : "+departmentId);
        }
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private EmployeeResponse mapToResponse(Employee employee){
        return  EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getName())
                .createdAt(employee.getCreatedAt())
                .build();

    }

}
