package com.fakhri.crudjpa.service;

import com.fakhri.crudjpa.dto.EmployeeRequestDto;
import com.fakhri.crudjpa.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDto create(EmployeeRequestDto employee);

    EmployeeResponseDto update(Integer id, EmployeeRequestDto employee) throws Exception;

    void delete(Integer id) throws Exception;

    List<EmployeeResponseDto> read();

    List<EmployeeResponseDto> findByNames(String name);

    EmployeeResponseDto findByName(String name) throws Exception;
}
