package com.fakhri.crudjpa.dto;

import lombok.Data;

@Data
public class EmployeeResponse {

    private Integer id;
    private String name;
    private String address;
    private DepartmentResponse department;
}
