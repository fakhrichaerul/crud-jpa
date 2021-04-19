package com.fakhri.crudjpa.service;

import com.fakhri.crudjpa.dto.EmployeeRequest;
import com.fakhri.crudjpa.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse create(EmployeeRequest employee) throws Exception;

    EmployeeResponse update(Integer id, EmployeeRequest employee) throws Exception;

    void delete(Integer id) throws Exception;

    List<EmployeeResponse> read();

    List<EmployeeResponse> findByNameList(String name);

    EmployeeResponse findByName(String name) throws Exception;
}
