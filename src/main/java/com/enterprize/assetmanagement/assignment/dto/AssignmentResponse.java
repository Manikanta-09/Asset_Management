package com.enterprize.assetmanagement.assignment.dto;


import com.enterprize.assetmanagement.assignment.model.AssignmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AssignmentResponse {
    private Long assignmentId;
    private Long assetId;
    private String assetName;
    private String serialNumber;
    private Long employeeId;
    private String employeeName;
    private AssignmentStatus status;
    private Instant assignedAt;
    private Instant returnedAt;
    private String notes;
}
