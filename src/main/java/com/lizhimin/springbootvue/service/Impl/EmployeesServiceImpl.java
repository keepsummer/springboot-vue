package com.lizhimin.springbootvue.service.Impl;

import com.lizhimin.springbootvue.entity.Employees;
import com.lizhimin.springbootvue.mapper.EmployeesMapper;
import com.lizhimin.springbootvue.service.EmployessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployessService {
    @Autowired
    EmployeesMapper employeesMapper;

    @Override
    public List<Employees> getEmpByName(String name) {

        return employeesMapper.getEmpByName();
    }
}
