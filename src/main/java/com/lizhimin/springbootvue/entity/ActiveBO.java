package com.lizhimin.springbootvue.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class ActiveBO implements Serializable {
    private static final long serialVersionUID = -5800782578272943999L;


    /*
     * id
     */
    @Id
    @Column(length =11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    /*
     * 标题
     */

    private String title;
    /*
     * 链接
     */
    private String link;
    /*
     * 用户id
     */
    private String poster;
    /*
     * 时间
     */
    private Date createTime;
    /*
     * 投票数量
     */
    private Integer votes;
         

        
}
