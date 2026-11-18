package com.enterprize.assetmanagement.department.service;

import com.enterprize.assetmanagement.asset.dto.AssetResponse;
import com.enterprize.assetmanagement.common.exception.DuplicateResourceException;
import com.enterprize.assetmanagement.common.exception.ResourceNotFoundException;
import com.enterprize.assetmanagement.department.dto.DepartmentCreateRequest;
import com.enterprize.assetmanagement.department.dto.DepartmentResponse;
import com.enterprize.assetmanagement.department.model.Department;
import com.enterprize.assetmanagement.department.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }
    @Override
    @Transactional
    public DepartmentResponse createDepartment(DepartmentCreateRequest request){
        if(departmentRepository.existsByCodeIgnoreCase(request.getCode().trim())){
            throw new DuplicateResourceException("Department with code "+request.getCode()+" already exists");
        }
        Department department = Department.builder()
                .name(request.getName())
                .code(request.getCode().toUpperCase().trim())
                .build();
        Department saved = departmentRepository.save(department);
        return mapToResponse(saved);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id){
        Department department = departmentRepository.findByIdWithAssetsGraph(id)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found with Id "+id));
        return mapToResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartmentsWithAssets() {
        return departmentRepository.findAllWithAssetsJoinFetch().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id){
        Department department = departmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Department not found with id "+id));
        if(!department.getAssets().isEmpty()){
            throw new IllegalStateException("Cannot delete department with active assigned to it.");
        }
        departmentRepository.delete(department);
    }
    private DepartmentResponse mapToResponse(Department dept) {
        List<AssetResponse> assetList = (dept.getAssets() == null)
                ? Collections.emptyList()
                : dept.getAssets().stream()
                .map(asset -> AssetResponse.builder()
                        .id(asset.getId())
                        .name(asset.getName())
                        .serialNumber(asset.getSerialNumber())
                        .assetType(asset.getAssetType())
                        .status(asset.getStatus())
                        .createdAt(asset.getCreatedAt())
                        .build())
                .toList();

        return DepartmentResponse.builder()
                .id(dept.getId())
                .name(dept.getName())
                .code(dept.getCode())
                .assetCount(assetList.size())
                .assets(assetList)
                .build();
    }

}
