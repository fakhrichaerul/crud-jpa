package com.fakhri.crudjpa.service;

import com.fakhri.crudjpa.dto.DepartmentRequestDto;
import com.fakhri.crudjpa.dto.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {

    DepartmentResponseDto create(DepartmentRequestDto department);

    DepartmentResponseDto update(Integer id, DepartmentRequestDto department) throws Exception;

    void delete(Integer id) throws Exception;

    List<DepartmentResponseDto> read();

    List<DepartmentResponseDto> findByDepartmentNameList(String departmentName);

    DepartmentResponseDto findByDepartmentName(String departmentName) throws Exception;
}
