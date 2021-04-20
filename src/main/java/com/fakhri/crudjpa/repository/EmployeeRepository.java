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
    Optional<Employee> findByName(String name);

    // Query menggunakan HQL
    @Query(value = "SELECT e FROM Employee e WHERE e.name = :name")
    Optional<Employee> findByNameWithHql(@Param("name") String name);

    // Query menggunakan Native SQL
    @Query(value = "SELECT * FROM employee e WHERE e.name = :name", nativeQuery = true)
    Optional<Employee> findByNameWithSql(@Param("name") String name);

    // Menggunakan Specification
    List<Employee> findAll(Specification<Employee> specification);


//    Uncomment untuk menggunakan salah satu query Native SQL atau HQL

//    @Query(value = "SELECT * FROM employee e WHERE e.name = :name AND e.address = :address", nativeQuery = true)
    @Query(value = "SELECT e FROM Employee e WHERE e.name = :name AND e.address = :address")
    Optional<Employee> findByNameAndAddress(@Param("name") String name,@Param("address") String address);

//    @Query(value = "SELECT * FROM Employee e WHERE " +
//            "e.name iLIKE %:name% OR " +
//            "e.address iLIKE %:address%", nativeQuery = true)
    @Query(value = "SELECT e FROM Employee e WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(e.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<Employee> findEmployeeListByNameAndAddress(@Param("name") String name,@Param("address") String address);



//    @Query(value = "SELECT * FROM employee e LEFT JOIN department d ON d.id = e.department_id WHERE " +
//            "e.name iLIKE %:name% OR " +
//            "d.department_name iLIKE %:departmentName%", nativeQuery = true)
    @Query(value = "SELECT e FROM Employee e LEFT JOIN FETCH e.department WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(e.department.departmentName) LIKE LOWER(CONCAT('%', :departmentName, '%'))")
    List<Employee> findEmployeeListByNameAndDepartmentName(@Param("name") String name,@Param("departmentName") String departmentName);
}
