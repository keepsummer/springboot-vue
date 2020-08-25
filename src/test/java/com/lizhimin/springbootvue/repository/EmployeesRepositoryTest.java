package com.lizhimin.springbootvue.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeesRepositoryTest {
    @Autowired
    EmployeesRepository employeesRepository;
    @Test
    void findAll(){
        System.out.println(employeesRepository.findAll());
    }

}