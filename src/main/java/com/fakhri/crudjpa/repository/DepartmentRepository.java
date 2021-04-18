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
    @Query(value = "select d from Department d where d.departmentName like %:departmentName%")
    Optional<Department> findByDepartmentNameWithHql(@Param("departmentName") String departmentName);

    // Query enggunakan SQL Native
    @Query(value = "SELECT * FROM department d WHERE d.department_name = :departmentName", nativeQuery = true)
    Optional<Department> findByDepartmentNameWithSql(@Param("departmentName") String departmentName);

    // Menggunakan Specification
    List<Department> findAll(Specification<Department> specification);
}
