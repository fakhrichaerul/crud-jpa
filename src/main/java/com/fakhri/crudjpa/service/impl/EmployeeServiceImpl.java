package com.fakhri.crudjpa.service.impl;

import com.fakhri.crudjpa.dto.EmployeeRequestDto;
import com.fakhri.crudjpa.dto.EmployeeResponseDto;
import com.fakhri.crudjpa.model.Employee;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeResponseDto create(EmployeeRequestDto employee) {

        Employee employeeEntity = new Employee();
        employeeEntity.setName(employee.getName());
        employeeEntity.setAddress(employee.getAddress());
        employeeEntity.setDepartmentId(employee.getDepartmentId());

        Employee employeeResponse = employeeRepository.save(employeeEntity);

        EmployeeResponseDto responseDto = new EmployeeResponseDto();
        responseDto.setId(employeeResponse.getId());
        responseDto.setName(employeeResponse.getName());
        responseDto.setAddress(employeeResponse.getAddress());
        responseDto.setDepartmentId(employee.getDepartmentId());

        return responseDto;
    }

    @Override
    public EmployeeResponseDto update(Integer id, EmployeeRequestDto employee) throws Exception {

        Optional<Employee> findEmployee = employeeRepository.findById(id);

        if (findEmployee.isEmpty()){
            throw new Exception("id not found.");
        }

        findEmployee.get().setName(employee.getName());
        findEmployee.get().setAddress(employee.getAddress());
        findEmployee.get().setDepartmentId(employee.getDepartmentId());
        Employee savedEmployee = employeeRepository.save(findEmployee.get());

        EmployeeResponseDto responseDto = new EmployeeResponseDto();
        responseDto.setId(savedEmployee.getId());
        responseDto.setName(savedEmployee.getName());
        responseDto.setAddress(savedEmployee.getAddress());
        responseDto.setDepartmentId(savedEmployee.getDepartmentId());

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

            EmployeeResponseDto responseDto = new EmployeeResponseDto();
            responseDto.setId(employee.getId());
            responseDto.setName(employee.getName());
            responseDto.setAddress(employee.getAddress());
            responseDto.setDepartmentId(employee.getDepartmentId());

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
    public List<EmployeeResponseDto> findByNames(String name) {

        List<Employee> employees = employeeRepository.findAll(specification(name));

        List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();
        employees.forEach(employee -> {
            EmployeeResponseDto responseDto = new EmployeeResponseDto();
            responseDto.setName(employee.getName());
            responseDto.setId(employee.getId());
            responseDto.setAddress(employee.getAddress());
            responseDto.setDepartmentId(employee.getDepartmentId());

            employeeResponseDtos.add(responseDto);
        });

        return employeeResponseDtos;
    }

    @Override
    public EmployeeResponseDto findByName(String name) throws Exception {

        Optional<Employee> findByName = employeeRepository.findNameBySql(name);

        if (findByName.isEmpty()) {
            throw new Exception("Employee name not found");
        }

        EmployeeResponseDto responseDto = new EmployeeResponseDto();
        responseDto.setName(findByName.get().getName());
        responseDto.setId(findByName.get().getId());
        responseDto.setAddress(findByName.get().getAddress());
        responseDto.setDepartmentId(findByName.get().getDepartmentId());

        return responseDto;
    }
}
