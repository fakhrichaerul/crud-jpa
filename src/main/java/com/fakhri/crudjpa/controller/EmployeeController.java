package com.fakhri.crudjpa.controller;

import com.fakhri.crudjpa.dto.EmployeeRequest;
import com.fakhri.crudjpa.dto.EmployeeResponse;
import com.fakhri.crudjpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeResponse create(@RequestBody EmployeeRequest requestDto) throws Exception {
        return employeeService.create(requestDto);
    }

    @PutMapping(value = "/{id}")
    public EmployeeResponse update(@PathVariable(value = "id") Integer id,
                                   @RequestBody EmployeeRequest requestDto) throws Exception {
        return employeeService.update(id, requestDto);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable(value = "id") Integer id) throws Exception {
        employeeService.delete(id);
        return "Delete success";
    }

    @GetMapping
    public List<EmployeeResponse> read(){
        return employeeService.read();
    }

    @GetMapping("/find-by-name-with-jpql")
    public EmployeeResponse readEmployeeByParamNameWithJpql(@RequestParam(name = "name") String name) throws Exception {
        return employeeService.findByName(name);
    }

    @GetMapping("/find-list-by-name-with-spec")
    public List<EmployeeResponse> readEmployeeListByParamNameWithSpec(@RequestParam(name = "name") String name) {
        return employeeService.findEmployeeListByNameWithSpec(name);
    }

    @GetMapping("/find-by-name-and-address")
    public EmployeeResponse findByParamNameAndAddress(@RequestParam(name = "name") String name,
                                                      @RequestParam(name = "address") String address) throws Exception {
        return employeeService.findByNameAndAddress(name, address);
    }
}
