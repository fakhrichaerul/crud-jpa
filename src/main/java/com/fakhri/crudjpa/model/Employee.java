package com.fakhri.crudjpa.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
