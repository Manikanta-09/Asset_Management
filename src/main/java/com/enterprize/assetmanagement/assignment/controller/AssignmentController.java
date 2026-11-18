package com.enterprize.assetmanagement.assignment.controller;


import com.enterprize.assetmanagement.assignment.dto.AssignmentResponse;
import com.enterprize.assetmanagement.assignment.dto.CheckInRequest;
import com.enterprize.assetmanagement.assignment.dto.CheckOutRequest;
import com.enterprize.assetmanagement.assignment.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService){
        this.assignmentService = assignmentService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<AssignmentResponse> checkout(@Valid @RequestBody CheckOutRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(assignmentService.checkOutAsset(request));
    }

    @PostMapping("/chekin/{assetId}")
    public ResponseEntity<AssignmentResponse> checkin(@PathVariable Long assetId, @RequestBody(required = false)CheckInRequest request){
        return ResponseEntity.ok(assignmentService.checkInAsset(assetId, request));
    }

    @GetMapping("/history/asset/{assetId}")
    public ResponseEntity<List<AssignmentResponse>> getHistory(@PathVariable Long assetId){
        return ResponseEntity.ok(assignmentService.getAssignmentHistoryByAsset(assetId));
    }
}
