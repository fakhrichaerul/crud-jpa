package com.fakhri.crudjpa.service.impl;

import com.fakhri.crudjpa.dto.DepartmentRequestDto;
import com.fakhri.crudjpa.dto.DepartmentResponseDto;
import com.fakhri.crudjpa.model.Department;
import com.fakhri.crudjpa.repository.DepartmentRepository;
import com.fakhri.crudjpa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponseDto create(DepartmentRequestDto department) {

        // Transform dari DTO ke Entity
        Department departmentEntity = new Department();
        departmentEntity.setDepartmentName(department.getDepartmentName());

        // Save to Database
        Department departmentResponse = departmentRepository.save(departmentEntity);

        // Transform dari Entity ke DTO
        DepartmentResponseDto responseDto = new DepartmentResponseDto();
        responseDto.setId(departmentResponse.getId());
        responseDto.setDepartmentName(departmentResponse.getDepartmentName());

        return responseDto;
    }

    @Override
    public DepartmentResponseDto update(Integer id, DepartmentRequestDto department) throws Exception {

        // Get data by id
        Optional<Department> findDepartment = departmentRepository.findById(id);

        // Jika id tidak ditemukan maka berikan error exception ke depan
        if (findDepartment.isEmpty()){
            throw new Exception("id not found.");
        }

        // Jika id ditemukan
        // set data lama ke data baru
        findDepartment.get().setDepartmentName(department.getDepartmentName());
        Department savedDepartment = departmentRepository.save(findDepartment.get());

        DepartmentResponseDto responseDto = new DepartmentResponseDto();
        responseDto.setId(savedDepartment.getId());
        responseDto.setDepartmentName(savedDepartment.getDepartmentName());

        return responseDto;
    }

    @Override
    public void delete(Integer id) throws Exception {

        Optional<Department> findDepartment = departmentRepository.findById(id);

        // Jika id tidak ditemukan maka berikan error exception ke depan
        if (findDepartment.isEmpty()){
            throw new Exception("id not found.");
        }

        departmentRepository.delete(findDepartment.get());

    }

    @Override
    public List<DepartmentResponseDto> read() {

        // findAll data department
        Iterable<Department> departments = departmentRepository.findAll();

        // Tampungan responsenya
        List<DepartmentResponseDto> departmentResponseDtos = new ArrayList<>();

        // Feeding data / transform data dari entity ke DTO
        departments.forEach(department -> {

            DepartmentResponseDto responseDto = new DepartmentResponseDto();
            responseDto.setId(department.getId());
            responseDto.setDepartmentName(department.getDepartmentName());

            departmentResponseDtos.add(responseDto);
        });

        return departmentResponseDtos;
    }

    // Membuat criteriaBuilder untuk departmentName
    private Specification specification(String departmentName){
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("departmentName"), departmentName);
        };
    }

    @Override
    public List<DepartmentResponseDto> findByDepartmentNameList(String departmentName) {

        List<Department> departments = departmentRepository.findAll(specification(departmentName));

        List<DepartmentResponseDto> departmentResponseDtos = new ArrayList<>();
        departments.forEach(department -> {
            DepartmentResponseDto responseDto = new DepartmentResponseDto();
            responseDto.setDepartmentName(department.getDepartmentName());
            responseDto.setId(department.getId());
            departmentResponseDtos.add(responseDto);
        });

        return departmentResponseDtos;
    }

    @Override
    public DepartmentResponseDto findByDepartmentName(String departmentName) throws Exception {

        Optional<Department> findByDepartmentName = departmentRepository.findDepartmentNameBySql(departmentName);

        if(findByDepartmentName.isEmpty()){
            throw new Exception("Department Name not found");
        }

        DepartmentResponseDto responseDto = new DepartmentResponseDto();
        responseDto.setDepartmentName(findByDepartmentName.get().getDepartmentName());
        responseDto.setId(findByDepartmentName.get().getId());

        return responseDto;
    }
}
