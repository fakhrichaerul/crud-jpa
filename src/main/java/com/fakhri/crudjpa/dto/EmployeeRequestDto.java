package com.fakhri.crudjpa.dto;

import lombok.Data;

@Data
public class EmployeeRequestDto {

    private String name;
    private String address;
    private Integer departmentId;
    private Integer departmentQuantity;
}
