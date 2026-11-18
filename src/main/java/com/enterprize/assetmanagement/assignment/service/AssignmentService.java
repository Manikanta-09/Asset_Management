package com.enterprize.assetmanagement.assignment.service;

import com.enterprize.assetmanagement.assignment.dto.AssignmentResponse;
import com.enterprize.assetmanagement.assignment.dto.CheckInRequest;
import com.enterprize.assetmanagement.assignment.dto.CheckOutRequest;

import java.util.List;

public interface AssignmentService {
    AssignmentResponse checkOutAsset(CheckOutRequest request);
    AssignmentResponse checkInAsset(Long assetId , CheckInRequest request);

    List<AssignmentResponse> getAssignmentHistoryByAsset(Long assetId);
}
