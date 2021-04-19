package com.fakhri.crudjpa.dto;

import lombok.Data;

@Data
public class EmployeeResponseDto {

    private Integer id;
    private String name;
    private String address;
    private DepartmentResponseDto department;
    private Integer departmentQuantity;
}
