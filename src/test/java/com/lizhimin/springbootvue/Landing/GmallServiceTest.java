package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GmallServiceTest {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    GmallService gmallService;

    /**
     * 设置token值
     */
    @Test
    public void test1(){
        long l = System.currentTimeMillis();
        String token = String.valueOf(l);
        redisUtil.hset("Login"+":"+token,token,"xiaoming");
    }

    /**
     * 获取token值
     */
    @Test
    public void test2(){
        String token ="1599211468854";
        String username = gmallService.checkToken(token);
        System.out.println(username);

    }
}