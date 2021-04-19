package com.fakhri.crudjpa.service.impl;

import com.fakhri.crudjpa.dto.DepartmentResponse;
import com.fakhri.crudjpa.dto.EmployeeRequest;
import com.fakhri.crudjpa.dto.EmployeeResponse;
import com.fakhri.crudjpa.model.Department;
import com.fakhri.crudjpa.model.Employee;
import com.fakhri.crudjpa.repository.DepartmentRepository;
import com.fakhri.crudjpa.repository.EmployeeRepository;
import com.fakhri.crudjpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    private Employee buildEmployeeModelFromRequest(EmployeeRequest employeeRequest, Department department) {
        Employee employee = new Employee();
        employee.setName(employeeRequest.getName());
        employee.setAddress(employeeRequest.getAddress());
        employee.setDepartment(department);
        return employee;
    }

    private EmployeeResponse buildEmployeeResponseFromModel(Employee employee, DepartmentResponse departmentResponse) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employee.getId());
        employeeResponse.setName(employee.getName());
        employeeResponse.setAddress(employee.getAddress());
        employeeResponse.setDepartment(departmentResponse);
        return employeeResponse;
    }

    private DepartmentResponse buildDepartmentResponseFromModel(Department department) {
        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setId(department.getId());
        departmentResponse.setDepartmentName(department.getDepartmentName());
        return departmentResponse;
    }

    @Override
    public EmployeeResponse create(EmployeeRequest employee) throws Exception {
        Optional<Department> findDepartment = departmentRepository.findById(employee.getDepartmentId());
        if(findDepartment.isEmpty()){
            throw new Exception("id not found");
        }
        Department department = findDepartment.get();

        Employee employeeModel = buildEmployeeModelFromRequest(employee, department);
        Employee employeeResponse = employeeRepository.save(employeeModel);
        DepartmentResponse departmentResponse = buildDepartmentResponseFromModel(department);

        EmployeeResponse responseDto = buildEmployeeResponseFromModel(employeeModel, departmentResponse);
        return responseDto;
    }

    @Override
    public EmployeeResponse update(Integer id, EmployeeRequest employee) throws Exception {
        Optional<Department> findDepartment = departmentRepository.findById(employee.getDepartmentId());
        if (findDepartment.isEmpty()){
            throw new Exception("id not found.");
        }
        Department department = findDepartment.get();
        
        Optional<Employee> findEmployee = employeeRepository.findById(id);
        if (findEmployee.isEmpty()){
            throw new Exception("id not found.");
        }
        findEmployee.get().setName(employee.getName());
        findEmployee.get().setAddress(employee.getAddress());
        findEmployee.get().setDepartment(department);

        Employee savedEmployee = employeeRepository.save(findEmployee.get());
        DepartmentResponse departmentResponse = buildDepartmentResponseFromModel(department);
        EmployeeResponse responseDto = buildEmployeeResponseFromModel(savedEmployee, departmentResponse);

        return responseDto;
    }

    @Override
    public void delete(Integer id) throws Exception {
        Optional<Employee> findEmployee = employeeRepository.findById(id);
        if (findEmployee.isEmpty()){
            throw new Exception("id not found.");
        }

        employeeRepository.delete(findEmployee.get());
    }

    @Override
    public List<EmployeeResponse> read() {
        Iterable<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();

        employees.forEach(employee -> {
            DepartmentResponse departmentResponse = buildDepartmentResponseFromModel(employee.getDepartment());
            EmployeeResponse responseDto = buildEmployeeResponseFromModel(employee, departmentResponse);

            employeeResponses.add(responseDto);
        });

        return employeeResponses;
    }

    @Override
    public EmployeeResponse findByName(String name) throws Exception {
        Optional<Employee> findByName = employeeRepository.findByName(name);
        if (findByName.isEmpty()) {
            throw new Exception("Employee name not found");
        }
        DepartmentResponse departmentResponse = buildDepartmentResponseFromModel(findByName.get().getDepartment());
        EmployeeResponse responseDto = buildEmployeeResponseFromModel(findByName.get(), departmentResponse);

        return responseDto;
    }

    private Specification specification(String name){
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    @Override
    public List<EmployeeResponse> findEmployeeListByNameWithSpec(String name) {
        List<Employee> employees = employeeRepository.findAll(specification(name));

        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        employees.forEach(employee -> {

            DepartmentResponse departmentResponse = buildDepartmentResponseFromModel(employee.getDepartment());

            EmployeeResponse responseDto = buildEmployeeResponseFromModel(employee, departmentResponse);

            employeeResponses.add(responseDto);
        });

        return employeeResponses;
    }

    @Override
    public EmployeeResponse findByNameAndAddress(String name, String address) throws Exception {
        Optional<Employee> findByNameAndAddress = employeeRepository.findByNameAndAddress(name, address);
        if (findByNameAndAddress.isEmpty()) {
            throw new Exception("name or address not found");
        }

        DepartmentResponse departmentResponse = buildDepartmentResponseFromModel(findByNameAndAddress.get().getDepartment());
        EmployeeResponse response = buildEmployeeResponseFromModel(findByNameAndAddress.get(), departmentResponse);

        return response;
    }
}
