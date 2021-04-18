package com.fakhri.crudjpa.controller;

import com.fakhri.crudjpa.dto.DepartmentRequestDto;
import com.fakhri.crudjpa.dto.DepartmentResponseDto;
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
    public DepartmentResponseDto create(@RequestBody DepartmentRequestDto requestDto) {
        return departmentService.create(requestDto);
    }

    @PutMapping(value = "/{id}")
    public DepartmentResponseDto update(@PathVariable(value = "id") Integer id,
                                        @RequestBody DepartmentRequestDto requestDto
    ) throws Exception {
        return departmentService.update(id, requestDto);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable(value = "id") Integer id) throws Exception {
        departmentService.delete(id);
        return "Success";
    }

    @GetMapping
    public List<DepartmentResponseDto> read() {
        return departmentService.read();
    }

    @GetMapping("/find-by-name-with-spec")
    public List<DepartmentResponseDto> readByParam(@RequestParam(name = "departmentName") String departmentName) {
        return departmentService.findByDepartmentNameList(departmentName);
    }

    @GetMapping("/find-by-name-with-hql")
    public  DepartmentResponseDto findDepartmentByName(@RequestParam(name = "departmentName") String departmentName) throws Exception{
        return departmentService.findByDepartmentName(departmentName);
    }
}
