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

    private Employee buildEmployeeModelFromRequest(EmployeeRequestDto employeeRequestDto, Department department) {
        Employee employee = new Employee();
        employee.setName(employeeRequestDto.getName());
        employee.setAddress(employeeRequestDto.getAddress());
        employee.setDepartment(department);
        return employee;
    }

    private EmployeeResponseDto buildEmployeeResponseFromModel(Employee employee, DepartmentResponseDto departmentResponseDto) {
        EmployeeResponseDto employeeResponse = new EmployeeResponseDto();
        employeeResponse.setId(employee.getId());
        employeeResponse.setName(employee.getName());
        employeeResponse.setAddress(employee.getAddress());
        employeeResponse.setDepartment(departmentResponseDto);
        return employeeResponse;
    }

    private DepartmentResponseDto buildDepartmentResponseFromModel(Department department) {
        DepartmentResponseDto departmentResponse = new DepartmentResponseDto();
        departmentResponse.setId(department.getId());
        departmentResponse.setDepartmentName(department.getDepartmentName());
        return departmentResponse;
    }

    @Override
    public EmployeeResponseDto create(EmployeeRequestDto employee) throws Exception {

        Optional<Department> findDepartment = departmentRepository.findById(employee.getDepartmentId());
        if(findDepartment.isEmpty()){
            throw new Exception("id not found");
        }
        Department department = findDepartment.get();

        Employee employeeModel = buildEmployeeModelFromRequest(employee, department);
        Employee employeeResponse = employeeRepository.save(employeeModel);
        DepartmentResponseDto departmentResponseDto = buildDepartmentResponseFromModel(department);

        EmployeeResponseDto responseDto = buildEmployeeResponseFromModel(employeeModel, departmentResponseDto);
        return responseDto;
    }

    @Override
    public EmployeeResponseDto update(Integer id, EmployeeRequestDto employee) throws Exception {

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

        DepartmentResponseDto departmentResponseDto = buildDepartmentResponseFromModel(department);

        EmployeeResponseDto responseDto = buildEmployeeResponseFromModel(savedEmployee, departmentResponseDto);

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
    public List<EmployeeResponseDto> read() {

        Iterable<Employee> employees = employeeRepository.findAll();

        List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();

        employees.forEach(employee -> {

            DepartmentResponseDto departmentResponseDto = buildDepartmentResponseFromModel(employee.getDepartment());

            EmployeeResponseDto responseDto = buildEmployeeResponseFromModel(employee, departmentResponseDto);

            employeeResponseDtos.add(responseDto);
        });

        return employeeResponseDtos;
    }

    private Specification specification(String name){
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    @Override
    public List<EmployeeResponseDto> findByNameList(String name) {

        List<Employee> employees = employeeRepository.findAll(specification(name));

        List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();
        employees.forEach(employee -> {

            DepartmentResponseDto departmentResponseDto = buildDepartmentResponseFromModel(employee.getDepartment());

            EmployeeResponseDto responseDto = buildEmployeeResponseFromModel(employee, departmentResponseDto);

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

        DepartmentResponseDto departmentResponseDto = buildDepartmentResponseFromModel(findByName.get().getDepartment());

        EmployeeResponseDto responseDto = buildEmployeeResponseFromModel(findByName.get(), departmentResponseDto);

        return responseDto;
    }
}
