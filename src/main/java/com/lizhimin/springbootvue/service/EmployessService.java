package com.lizhimin.springbootvue.service;

import com.lizhimin.springbootvue.entity.Employees;

import java.util.List;

public interface EmployessService {
    List<Employees> getEmpByName(String name);
}
