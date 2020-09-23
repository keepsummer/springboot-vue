package com.lizhimin.springbootvue.mapper;

import com.lizhimin.springbootvue.entity.Employees;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesMapper {
    List<Employees> getEmpByName();
}
