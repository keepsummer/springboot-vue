package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.listener.DelListenerPOIScheduler;
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
    @Autowired
    DelListenerPOIScheduler delListenerPOIScheduler;

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

    /**
     * 获取token值
     */
    @Test
    public void test3(){
        String token ="1599211468854";
        gmallService.updateToken(token,"lisi","2020090211202600006");

    }
    /**
     * 获取token值
     */
    @Test
    public void test4(){
        for (int i = 0; i < 30; i++) {
            String token ="1599211468854"+i;
            Long itemId = 2020090211202600006L+i;
            gmallService.updateToken(token,"lisi"+i,String.valueOf(itemId));

        }

    }
    @Test
    public void test5(){
        for (int i = 0; i < 30; i++) {
           redisUtil.zset("listen"+":"+i,String.valueOf(i),300D*i);
        }

    }
    @Test
    public void test6(){
        System.out.println(redisUtil.zRange("recent:", 0L, -1L));
    }
}