package com.lizhimin.springbootvue.controller;

import com.lizhimin.springbootvue.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class EmployeesHandler {

    @Autowired
    EmployeesRepository employeesRepository;

    public
}
