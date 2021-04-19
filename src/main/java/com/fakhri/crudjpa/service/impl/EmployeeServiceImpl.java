package com.fakhri.crudjpa.service.impl;

import com.fakhri.crudjpa.dto.DepartmentResponseDto;
import com.fakhri.crudjpa.dto.EmployeeRequestDto;
import com.fakhri.crudjpa.dto.EmployeeResponseDto;
import com.fakhri.crudjpa.model.Department;
import com.fakhri.crudjpa.model.Employee;
import com.fakhri.crudjpa.repository.DepartmentRepository;
import com.fakhri.crudjpa.repository.EmployeeRepository;
import com.fakhri.crudjpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    // Transform Employee Request DTO ke Model
    private Employee buildEmployeeModelFromRequest(EmployeeRequestDto request, Department department) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setAddress(request.getAddress());
        employee.setDepartment(department);
        employee.setDepartmentQuantity(request.getDepartmentQuantity());
        return employee;
    }

    // Transform Employee Model ke Response DTO
    private EmployeeResponseDto buildEmployeeResponseFromModel(Employee savedEmployee) {
        EmployeeResponseDto response = new EmployeeResponseDto();
        response.setId(savedEmployee.getId());
        response.setName(savedEmployee.getName());
        response.setAddress(savedEmployee.getAddress());
        DepartmentResponseDto departmentResponse = buildDepartmentResponseFromModel(savedEmployee.getDepartment());
        response.setDepartment(departmentResponse);
        response.setDepartmentQuantity(savedEmployee.getDepartmentQuantity());
        return response;
    }

    // Transform Department Model ke Response DTO
    private DepartmentResponseDto buildDepartmentResponseFromModel(Department department) {
        DepartmentResponseDto response = new DepartmentResponseDto();
        response.setId(department.getId());
        response.setDepartmentName(department.getDepartmentName());
        response.setQuantity(department.getQuantity());
        return response;
    }

    @Override
    @Transactional
    public EmployeeResponseDto create(EmployeeRequestDto request) throws Exception {
        Optional<Department> findDepartment = departmentRepository.findById(request.getDepartmentId());
        if (findDepartment.isEmpty()) {
            throw new Exception("departmentId not found");
        }
        Department department = findDepartment.get();

        Employee employee = buildEmployeeModelFromRequest(request, department);
        Employee savedEmployee = employeeRepository.save(employee);

        // Business Logic : ketika departmentQuantity di table Employee dibuat maka,
        //                  Quantity di table Department dikurangi departmentQuantity di table Employee
        Integer newQuantity = department.getQuantity() - request.getDepartmentQuantity();
        department.setQuantity(newQuantity);
        departmentRepository.save(department);

        EmployeeResponseDto response = buildEmployeeResponseFromModel(savedEmployee);

        return response;
    }

    @Override
    public EmployeeResponseDto update(Integer id, EmployeeRequestDto request) throws Exception {
        Optional<Department> findDepartment = departmentRepository.findById(request.getDepartmentId());
        if (findDepartment.isEmpty()) {
            throw new Exception("departmentId not found.");
        }
        Department department = findDepartment.get();

        Optional<Employee> findEmployee = employeeRepository.findById(id);
        if (findEmployee.isEmpty()) {
            throw new Exception("id not found.");
        }
        findEmployee.get().setName(request.getName());
        findEmployee.get().setAddress(request.getAddress());
        findEmployee.get().setDepartment(department);
        findEmployee.get().setDepartmentQuantity(request.getDepartmentQuantity());

        Employee savedEmployee = employeeRepository.save(findEmployee.get());

//        Integer oldQuantity = department.getQuantity() + department.getQuantity();
//        department.setQuantity(oldQuantity);
        Integer newQuantity = department.getQuantity() - request.getDepartmentQuantity();
        department.setQuantity(newQuantity);
        departmentRepository.save(department);

        EmployeeResponseDto responseDto = buildEmployeeResponseFromModel(savedEmployee);
        return responseDto;
    }

    @Override
    public void delete(Integer id) throws Exception {

        Optional<Employee> findEmployee = employeeRepository.findById(id);

        if (findEmployee.isEmpty()) {
            throw new Exception("id not found.");
        }

        employeeRepository.delete(findEmployee.get());

    }

    @Override
    public List<EmployeeResponseDto> read() {
        Iterable<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();

        employees.forEach(employee -> {
            EmployeeResponseDto responseDto = buildEmployeeResponseFromModel(employee);
            employeeResponseDtos.add(responseDto);
        });

        return employeeResponseDtos;
    }

    private Specification specification(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    @Override
    public List<EmployeeResponseDto> findByNameList(String name) {
        List<Employee> employees = employeeRepository.findAll(specification(name));
        List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();

        employees.forEach(employee -> {

            EmployeeResponseDto responseDto = buildEmployeeResponseFromModel(employee);

            employeeResponseDtos.add(responseDto);
        });

        return employeeResponseDtos;
    }

    @Override
    public EmployeeResponseDto findByName(String name) throws Exception {
        Optional<Employee> findByName = employeeRepository.findByNameWithSql(name);
        if (findByName.isEmpty()) {
            throw new Exception("Employee name not found");
        }
        EmployeeResponseDto responseDto = buildEmployeeResponseFromModel(findByName.get());

        return responseDto;
    }
}
