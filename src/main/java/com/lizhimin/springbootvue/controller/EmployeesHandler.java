package com.lizhimin.springbootvue.controller;

import com.lizhimin.springbootvue.entity.Employees;
import com.lizhimin.springbootvue.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeesHandler {

    @Autowired
    EmployeesRepository employeesRepository;

    @GetMapping("/findAll/{page}/{size}")
     public Page<Employees> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
       Pageable pageable = PageRequest.of(page-1,size);
        return employeesRepository.findAll(pageable);
    }
    @GetMapping("/findAll")
    public List<Employees> findAll(){
        return employeesRepository.findAll();
    }

    @PostMapping("/addEmployess")
    public String addEmployees(){
        return "aa";
    }
}
