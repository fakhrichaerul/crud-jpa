package com.fakhri.crudjpa.repository;

import com.fakhri.crudjpa.model.Department;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    // Query Menggunakan HQL
    @Query(value = "select d from Department d where d.departmentName = :departmentName")
    Optional<Department> findDepartmentNameByHql(@Param("departmentName") String departmentName);

    // Query Menggunakan SQL Native
    @Query(value = "SELECT * FROM department d WHERE d.department_name = :departmentName", nativeQuery = true)
    Optional<Department> findDepartmentNameBySql(@Param("departmentName") String departmentName);

    // Menggunakan Specification
    List<Department> findAll(Specification<Department> specification);
}
