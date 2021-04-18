package com.fakhri.crudjpa.repository;

import com.fakhri.crudjpa.model.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    // Query menggunakan JPA
    Optional<Employee> findByName(String departmentName);

    // Query menggunakan HQL
    @Query(value = "SELECT e FROM Employee e WHERE e.name = :name")
    Optional<Employee> findByNameWithHql(@Param("name") String name);

    // Query menggunakan SQL Native
    @Query(value = "SELECT * FROM employee e WHERE e.name iLIKE %:name%", nativeQuery = true)
    Optional<Employee> findByNameWithSql(@Param("name") String name);

    // Menggunakan Specification
    List<Employee> findAll(Specification<Employee> specification);
}
