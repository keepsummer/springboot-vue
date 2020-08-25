package com.lizhimin.springbootvue.repository;

import com.lizhimin.springbootvue.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employees,Integer> {
}
