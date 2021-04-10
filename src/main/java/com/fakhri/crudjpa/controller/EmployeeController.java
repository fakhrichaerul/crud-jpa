package com.fakhri.crudjpa.controller;

import com.fakhri.crudjpa.dto.EmployeeRequestDto;
import com.fakhri.crudjpa.dto.EmployeeResponseDto;
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
    public EmployeeResponseDto create(@RequestBody EmployeeRequestDto requestDto) throws Exception {
        return employeeService.create(requestDto);
    }

    @PutMapping(value = "/{id}")
    public EmployeeResponseDto update(@PathVariable(value = "id") Integer id,
                                      @RequestBody EmployeeRequestDto requestDto) throws Exception {
        return employeeService.update(id, requestDto);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable(value = "id") Integer id) throws Exception {
        employeeService.delete(id);
        return "Delete success";
    }

    @GetMapping
    public List<EmployeeResponseDto> read(){
        return employeeService.read();
    }

    @GetMapping("/find-by-name-by-spec")
    public List<EmployeeResponseDto> readByParam(@RequestParam(name = "name") String name) {
        return employeeService.findByNames(name);
    }

    @GetMapping("/find-by-name")
    public EmployeeResponseDto findEmployeeByName(@RequestParam(name = "name") String name) throws Exception {
        return employeeService.findByName(name);
    }
}
