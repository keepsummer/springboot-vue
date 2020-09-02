package com.lizhimin.springbootvue.redis;

import com.lizhimin.springbootvue.Landing.ActiveService;
import com.lizhimin.springbootvue.entity.Active;
import com.lizhimin.springbootvue.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ActiveService activeService;

    public Object getRedis(){
        redisUtil.set("r","aaaaaa");
        return redisUtil.get("r");
    }
    @Test
    public void test(){
        System.out.println(getRedis());
        System.out.println(System.currentTimeMillis());
    }
    @Test
    public void test1(){
        List<Active> list = new ArrayList<>();
        Active active = new Active();
        active.setId(1L);
        active.setTitle("肖申克的救赎");
        active.setLink("https://www.cnblogs.com/minmin123/p/13595734.html");
        active.setPoster("user01");
        active.setVotes(0);
        Active active1 = new Active();
        active1.setId(1L);
        active1.setTitle("如何提升工作效率");
        active1.setLink("https://www.baidu.com");
        active1.setPoster("user02");
        active1.setVotes(0);
        list.add(active);
        list.add(active1);

        Integer i = 0;
        for (Active active0:list) {

            redisUtil.hset("active："+i, "title",active0.getTitle());
            redisUtil.hset("active："+i, "link",active0.getLink());
            redisUtil.hset("active："+i, "poster",active0.getPoster());
            redisUtil.hset("active："+i, "time",System.currentTimeMillis());
            redisUtil.hset("active："+i, "votes",active0.getVotes());
            i++;
        }







    }
    @Test
    public void test3(){
        redisUtil.zset("time","active：0",1598945519501D);
        redisUtil.zset("time","active：1",1598945519511D);
    }
    @Test
    public void test4(){
        redisUtil.zset("score","active：0",0D);
        redisUtil.zset("score","active：1",0D);
    }
    @Test
    public void test5(){
        redisUtil.sSet("voted:0","user03");
        redisUtil.sSet("voted:0","user04");
        redisUtil.zset("score","active：0",864D);
        redisUtil.hset("active：0", "votes",2);
    }
    @Test
    public void test6() {
        activeService.articleVote("0","user01");
        Object time = redisUtil.zget("time", "active：0");
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        String format = nf.format(time);
        System.out.println(format);

    }
    @Test
    public void test7() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        System.out.println(format);

        System.out.println(new Date().toString());
    }
    @Test
    public void test8() {
       Active active = new Active();
       active.setTitle("如何把大象放进冰箱");
       active.setLink("https://www.daxiang.com");
       active.setPoster("user:000007");
       active.setVotes(0);
       activeService.postActive(active);
    }

}
