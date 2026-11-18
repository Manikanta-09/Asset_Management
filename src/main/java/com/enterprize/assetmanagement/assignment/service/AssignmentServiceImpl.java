package com.enterprize.assetmanagement.assignment.service;


import com.enterprize.assetmanagement.asset.model.Asset;
import com.enterprize.assetmanagement.asset.model.AssetStatus;
import com.enterprize.assetmanagement.asset.repository.AssetRepository;
import com.enterprize.assetmanagement.assignment.dto.AssignmentResponse;
import com.enterprize.assetmanagement.assignment.dto.CheckInRequest;
import com.enterprize.assetmanagement.assignment.dto.CheckOutRequest;
import com.enterprize.assetmanagement.assignment.model.AssetAssignment;
import com.enterprize.assetmanagement.assignment.model.AssignmentStatus;
import com.enterprize.assetmanagement.assignment.repository.AssetAssignmentRepository;
import com.enterprize.assetmanagement.common.exception.ResourceNotFoundException;
import com.enterprize.assetmanagement.employee.model.Employee;
import com.enterprize.assetmanagement.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AssignmentServiceImpl implements AssignmentService{
    private final AssetRepository assetRepository;
    private final EmployeeRepository employeeRepository;
    private final AssetAssignmentRepository assetAssignmentRepository;

    public  AssignmentServiceImpl(AssetRepository assetRepository , EmployeeRepository employeeRepository , AssetAssignmentRepository assetAssignmentRepository){
        this.assetRepository = assetRepository;
        this.employeeRepository = employeeRepository;
        this.assetAssignmentRepository = assetAssignmentRepository;
    }
    @Transactional
    public AssignmentResponse checkOutAsset(CheckOutRequest request){
        Asset asset = assetRepository.findById(request.getAssetId())
                .orElseThrow(()-> new ResourceNotFoundException("Asset not found with request Id "+ request.getAssetId()));

        if(asset.getStatus()!= AssetStatus.AVAILABLE ){
            throw new IllegalStateException("Asset cannot be checkout. Current status : "+asset.getStatus());
        }
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id : "+request.getEmployeeId()));
        asset.setStatus(AssetStatus.ASSIGNED);
        assetRepository.save(asset);

        AssetAssignment assignment = AssetAssignment.builder()
                .asset(asset)
                .employee(employee)
                .assignedAt(Instant.now())
                .status(AssignmentStatus.ACTIVE)
                .notes(request.getNotes())
                .build();
        AssetAssignment savedAssignment = assetAssignmentRepository.save(assignment);
        return mapToResponse(savedAssignment);

    }

    @Override
    @Transactional
    public AssignmentResponse checkInAsset(Long assetId, CheckInRequest request) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(()-> new ResourceNotFoundException("Asset not found with id : "+ assetId));

        if(asset.getStatus() != AssetStatus.ASSIGNED){
            throw new IllegalStateException("Asset cannot be resolved . Current Status "+asset.getStatus());
        }
        AssetAssignment assignment = assetAssignmentRepository.findByAssetIdAndStatus(assetId, AssignmentStatus.ACTIVE)
                .orElseThrow(()-> new ResourceNotFoundException("No active assignment found for Asset id "+assetId));

        assignment.setStatus(AssignmentStatus.RETURNED);
        assignment.setReturnedAt(Instant.now());
        if(request != null && request.getNotes() != null){
            assignment.setNotes(request.getNotes());
        }
        assetAssignmentRepository.save(assignment);

        //update the asset availability
        asset.setStatus(AssetStatus.AVAILABLE);
        assetRepository.save(asset);
        return mapToResponse(assignment);
    }

    public List<AssignmentResponse> getAssignmentHistoryByAsset(Long assetId){
        if(!assetRepository.existsById(assetId)){
            throw new ResourceNotFoundException("Asset not found wit ID: "+assetId);
        }
        return assetAssignmentRepository.findByAssetIdOrderByAssignedAtDesc(assetId).stream()
                .map(this::mapToResponse).toList();
    }

    public AssignmentResponse mapToResponse(AssetAssignment assetAssignment){
        return AssignmentResponse.builder().
                assignmentId(assetAssignment.getId())
                .assetId(assetAssignment.getAsset().getId())
                .assetName(assetAssignment.getAsset().getName())
                .serialNumber(assetAssignment.getAsset().getSerialNumber())
                .employeeId(assetAssignment.getEmployee().getId())
                .employeeName(assetAssignment.getEmployee().getFirstName() + " " + assetAssignment.getEmployee().getLastName())
                .status(assetAssignment.getStatus())
                .assignedAt(assetAssignment.getAssignedAt())
                .returnedAt(assetAssignment.getReturnedAt())
                .notes(assetAssignment.getNotes())
                .build();
    }

}
