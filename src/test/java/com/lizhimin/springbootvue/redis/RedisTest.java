package com.lizhimin.springbootvue.redis;

import com.lizhimin.springbootvue.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtil redisUtil;

    public Object getRedis(){
        redisUtil.set("r","aaaaaa");
        return redisUtil.get("r");
    }
    @Test
    public void test(){
        System.out.println(getRedis());
    }

}
