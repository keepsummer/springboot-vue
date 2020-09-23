package com.lizhimin.springbootvue.controller;

import com.lizhimin.springbootvue.entity.Employees;
import com.lizhimin.springbootvue.service.EmployessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/employees")
@CrossOrigin
public class EmployeesController {

    @Autowired
    EmployessService employessService;

    @GetMapping("getEmp/{name}")
    public List<Employees> getEmpByName(@PathVariable String name){
        return employessService.getEmpByName(name);
    }
}
