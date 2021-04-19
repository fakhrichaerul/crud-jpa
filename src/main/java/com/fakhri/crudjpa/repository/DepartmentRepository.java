package com.fakhri.crudjpa.repository;

import com.fakhri.crudjpa.model.Department;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    // Query menggunakan JPA
    Optional<Department> findByDepartmentName(String departmentName);

    // Query menggunakan HQL
    @Query(value = "SELECT d FROM Department d WHERE d.departmentName = :departmentName")
    Optional<Department> findByDepartmentNameWithHql(@Param("departmentName") String departmentName);

    // Query enggunakan SQL Native
    @Query(value = "SELECT * FROM department d WHERE d.department_name = :departmentName", nativeQuery = true)
    Optional<Department> findByDepartmentNameWithSql(@Param("departmentName") String departmentName);

    // Menggunakan Specification
    List<Department> findAll(Specification<Department> specification);

    @Query (value = "SELECT d FROM Department d WHERE LOWER(d.departmentName) LIKE LOWER(CONCAT('%', :departmentName, '%'))")
    List<Department> findDepartmentListByDepartmentNameWithHql(@Param("departmentName") String departmentName);

    @Query(value = "SELECT * FROM department d WHERE d.department_name iLIKE %:departmentName%", nativeQuery = true)
    List<Department> findDepartmentListByDepartmentNameWithSql(@Param("departmentName") String departmentName);
}
