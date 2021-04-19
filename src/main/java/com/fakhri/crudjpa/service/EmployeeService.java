package com.fakhri.crudjpa.service;

import com.fakhri.crudjpa.dto.EmployeeRequest;
import com.fakhri.crudjpa.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse create(EmployeeRequest employee) throws Exception;

    EmployeeResponse update(Integer id, EmployeeRequest employee) throws Exception;

    void delete(Integer id) throws Exception;

    EmployeeResponse findByName(String name) throws Exception;

    List<EmployeeResponse> read();

    List<EmployeeResponse> findEmployeeListByNameWithSpec(String name);

    EmployeeResponse findByNameAndAddress(String name, String address) throws Exception;
}
