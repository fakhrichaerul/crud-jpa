package com.fakhri.crudjpa.controller;

import com.fakhri.crudjpa.dto.DepartmentRequest;
import com.fakhri.crudjpa.dto.DepartmentResponse;
import com.fakhri.crudjpa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentResponse create(@RequestBody DepartmentRequest requestDto) {
        return departmentService.create(requestDto);
    }

    @PutMapping(value = "/{id}")
    public DepartmentResponse update(@PathVariable(value = "id") Integer id,
                                     @RequestBody DepartmentRequest requestDto
    ) throws Exception {
        return departmentService.update(id, requestDto);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable(value = "id") Integer id) throws Exception {
        departmentService.delete(id);
        return "Success";
    }

    @GetMapping
    public List<DepartmentResponse> read() {
        return departmentService.read();
    }

    @GetMapping("/find-by-name-with-jpql")
    public DepartmentResponse findDepartmentByNameWithJpql(@RequestParam(name = "departmentName") String departmentName) throws Exception{
        return departmentService.findByDepartmentName(departmentName);
    }

    @GetMapping("/find-by-name-with-hql")
    public DepartmentResponse findDepartmentByNameWithHql(@RequestParam(name = "departmentName") String departmentName) throws Exception{
        return departmentService.findByDepartmentNameWithHql(departmentName);
    }

    @GetMapping("/find-by-name-with-sql")
    public DepartmentResponse findDepartmentByNameWithSql(@RequestParam(name = "departmentName") String departmentName) throws Exception{
        return departmentService.findByDepartmentNameWithSql(departmentName);
    }

    @GetMapping("/find-by-name-with-spec")
    public List<DepartmentResponse> findDepartmentNameWithSpec(@RequestParam(name = "departmentName") String departmentName) {
        return departmentService.findByDepartmentNameWithSpec(departmentName);
    }

    @GetMapping("/search-by-name-with-hql")
    public List<DepartmentResponse> findDepartmentListByNameWithHql(@RequestParam(name = "departmentName") String departmentName) {
        return departmentService.searchByDepartmentNameWithHql(departmentName);
    }

    @GetMapping("/search-by-name-with-sql")
    public List<DepartmentResponse> findDepartmentListByNameWithSql(@RequestParam(name = "departmentName") String departmentName) {
        return departmentService.searchByDepartmentNameWithHql(departmentName);
    }
}
