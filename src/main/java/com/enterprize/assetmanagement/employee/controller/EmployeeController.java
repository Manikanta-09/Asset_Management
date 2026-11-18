package com.enterprize.assetmanagement.employee.controller;

import com.enterprize.assetmanagement.employee.dto.EmployeeCreateRequest;
import com.enterprize.assetmanagement.employee.dto.EmployeeResponse;
import com.enterprize.assetmanagement.employee.service.EmployeeService;
import com.enterprize.assetmanagement.employee.service.EmployeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    public final EmployeeService employeeService;
    private EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestParam(required = false) Long departmentId){
        if(departmentId != null){
            return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
        }
        return ResponseEntity.ok(employeeService.getAllEmployees());

    }


}
