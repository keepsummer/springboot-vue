package com.lizhimin.springbootvue.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Employees implements Serializable {
    private static final long serialVersionUID = -5809782578272943999L;
    /*
     * id
     */
    @Id
    @Column(length =11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;
    /*
     * 名字
     */
    @Column(length =25)
    private String name ;
     /*
      * 部门编号
      */
     @Column(length =11)
     private Integer deptid;
     /*
      * 工资
      */
     private Double salary;

}
