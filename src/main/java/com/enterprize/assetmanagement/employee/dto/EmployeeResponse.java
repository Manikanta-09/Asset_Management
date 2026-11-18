package com.enterprize.assetmanagement.employee.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long departmentId;
    private String departmentName;
    private Instant createdAt;
}
