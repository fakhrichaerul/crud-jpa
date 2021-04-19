package com.fakhri.crudjpa.dto;

import lombok.Data;

@Data
public class EmployeeRequest {

    private String name;
    private String address;
    private Integer departmentId;
}
