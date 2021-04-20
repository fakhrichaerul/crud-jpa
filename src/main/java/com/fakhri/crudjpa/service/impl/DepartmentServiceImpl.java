package com.fakhri.crudjpa.service.impl;

import com.fakhri.crudjpa.dto.DepartmentRequest;
import com.fakhri.crudjpa.dto.DepartmentResponse;
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
    public DepartmentResponse create(DepartmentRequest request) {

        // Transform dari Request ke Model
        Department department = new Department();
        department.setDepartmentName(request.getDepartmentName());

        // Save to Database
        Department savedDepartment = departmentRepository.save(department);

        // Transform dari Model ke Response
        DepartmentResponse response = new DepartmentResponse();
        response.setId(savedDepartment.getId());
        response.setDepartmentName(savedDepartment.getDepartmentName());

        return response;
    }

    @Override
    public DepartmentResponse update(Integer id, DepartmentRequest department) throws Exception {

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

        DepartmentResponse response = new DepartmentResponse();
        response.setId(savedDepartment.getId());
        response.setDepartmentName(savedDepartment.getDepartmentName());

        return response;
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
    public List<DepartmentResponse> read() {

        // findAll data department
        Iterable<Department> departments = departmentRepository.findAll();

        // Tampungan responsenya
        List<DepartmentResponse> departmentResponses = new ArrayList<>();

        // Feeding data / transform data dari entity ke DTO
        departments.forEach(department -> {

            DepartmentResponse response = new DepartmentResponse();
            response.setId(department.getId());
            response.setDepartmentName(department.getDepartmentName());

            departmentResponses.add(response);
        });

        return departmentResponses;
    }

    @Override
    public DepartmentResponse findByDepartmentName(String departmentName) throws Exception {
        Optional<Department> findByDepartmentName = departmentRepository.findByDepartmentName(departmentName);

        if(findByDepartmentName.isEmpty()){
            throw new Exception("Department Name not found");
        }
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentName(findByDepartmentName.get().getDepartmentName());
        response.setId(findByDepartmentName.get().getId());

        return response;
    }

    // Membuat criteriaBuilder untuk departmentName specification
    private Specification specification(String departmentName){
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("departmentName"), departmentName);
        };
    }

    @Override
    public List<DepartmentResponse> findDepartmentListByDepartmentNameWithSpec(String departmentName) {

        List<Department> departments = departmentRepository.findAll(specification(departmentName));

        List<DepartmentResponse> responseList = new ArrayList<>();
        departments.forEach(department -> {
            DepartmentResponse response = new DepartmentResponse();
            response.setDepartmentName(department.getDepartmentName());
            response.setId(department.getId());
            responseList.add(response);
        });

        return responseList;
    }

    @Override
    public DepartmentResponse findByDepartmentNameWithHql(String departmentName) throws Exception {
        Optional<Department> findByDepartmentName = departmentRepository.findByDepartmentNameWithHql(departmentName);
        if(findByDepartmentName.isEmpty()){
            throw new Exception("Department Name not found");
        }
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentName(findByDepartmentName.get().getDepartmentName());
        response.setId(findByDepartmentName.get().getId());

        return response;
    }

    @Override
    public DepartmentResponse findByDepartmentNameWithSql(String departmentName) throws Exception {
        Optional<Department> findByDepartmentName = departmentRepository.findByDepartmentNameWithSql(departmentName);
        if(findByDepartmentName.isEmpty()){
            throw new Exception("Department Name not found");
        }
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentName(findByDepartmentName.get().getDepartmentName());
        response.setId(findByDepartmentName.get().getId());

        return response;
    }

    @Override
    public List<DepartmentResponse> findDepartmentListByDepartmentNameWithHql(String departmentName) {
        List<Department> departments = departmentRepository.findDepartmentListByDepartmentNameWithHql(departmentName);

        List<DepartmentResponse> responseList = new ArrayList<>();
        departments.forEach(department -> {
            DepartmentResponse response = new DepartmentResponse();
            response.setDepartmentName(department.getDepartmentName());
            response.setId(department.getId());
            responseList.add(response);
        });

        return responseList;
    }

    @Override
    public List<DepartmentResponse> findDepartmentListByDepartmentNameWithSql(String departmentName) {
        List<Department> departments = departmentRepository.findDepartmentListByDepartmentNameWithSql(departmentName);

        List<DepartmentResponse> responseList = new ArrayList<>();
        departments.forEach(department -> {
            DepartmentResponse response = new DepartmentResponse();
            response.setDepartmentName(department.getDepartmentName());
            response.setId(department.getId());
            responseList.add(response);
        });

        return responseList;
    }
}
