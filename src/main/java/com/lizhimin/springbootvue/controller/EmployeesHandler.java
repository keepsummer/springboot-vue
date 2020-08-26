package com.lizhimin.springbootvue.controller;

import com.lizhimin.springbootvue.entity.Employees;
import com.lizhimin.springbootvue.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesHandler {

    @Autowired
    EmployeesRepository employeesRepository;
    @GetMapping("/findAll")
     public List<Employees> findAll(){
        return employeesRepository.findAll();
    }
}
