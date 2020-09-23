package com.lizhimin.springbootvue.redis;

import com.lizhimin.springbootvue.Landing.ActiveService;
import com.lizhimin.springbootvue.entity.ActiveBO;
import com.lizhimin.springbootvue.utils.RedisUtil;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        List<ActiveBO> list = new ArrayList<>();
        ActiveBO activeBO = new ActiveBO();
        activeBO.setId(1L);
        activeBO.setTitle("肖申克的救赎");
        activeBO.setLink("https://www.cnblogs.com/minmin123/p/13595734.html");
        activeBO.setPoster("user01");
        activeBO.setVotes(0);
        ActiveBO activeBO1 = new ActiveBO();
        activeBO1.setId(1L);
        activeBO1.setTitle("如何提升工作效率");
        activeBO1.setLink("https://www.baidu.com");
        activeBO1.setPoster("user02");
        activeBO1.setVotes(0);
        list.add(activeBO);
        list.add(activeBO1);

        Integer i = 0;
        for (ActiveBO activeBO0 :list) {

            redisUtil.hset("active："+i, "title", activeBO0.getTitle());
            redisUtil.hset("active："+i, "link", activeBO0.getLink());
            redisUtil.hset("active："+i, "poster", activeBO0.getPoster());
            redisUtil.hset("active："+i, "time",System.currentTimeMillis());
            redisUtil.hset("active："+i, "votes", activeBO0.getVotes());
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
//        activeService.articleVote("2020090415122300009","user:000014");
//        activeService.articleVote("2020090415122300009","user:000014");
//        activeService.articleVote("2020090415143600011","user:000014");
//        activeService.articleVote("2020090211184300005","user:000014");
//        activeService.articleVote("2020090415155000012","user:000014");
//        activeService.articleVote("2020090415133700010","user:000014");
//        Object time = redisUtil.zget("time", "active：0");
//        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
//        nf.setGroupingUsed(false);
//        String format = nf.format(time);
//        System.out.println(format);

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
       ActiveBO activeBO = new ActiveBO();
       activeBO.setTitle("竹纤维纸抽的制作");
       activeBO.setLink("https://www.zhichou.com");
       activeBO.setPoster("user:000012");
       activeBO.setVotes(0);
       activeService.postActive(activeBO);
    }
    @Test
    public void test9() {
        Double max = activeService.turnLongToString(System.currentTimeMillis());
        List<Map> activesByCreatetime = activeService.getActivesByCreatetime(1599016826069D, max);
        //List<Map> activesList = activeService.getActivesByScore(0D,876D);
        System.out.println(activesByCreatetime);

    }
    @Test
    public void test10() {
       // Long aLong = activeService.addGroups("2020090211202600006","jiaoyu");
//        activeService.addGroups("2020090211184300005","java");
//        activeService.addGroups("2020090415090000008","java");
//        activeService.addGroups("2020090415100600008","java");
        activeService.addGroups("2020090211202600006","java");


    }
    @Test
    public void test11() {
        Long aLong = activeService.delGroup("2020090211184300005","jingji1");

    }

    @Test
    public void test12() {
        activeService.getGroupsActives("java");

    }

    @Test
    public void test13() {
        Integer a =1;
        while (a<10){
          a++;
          System.out.println("a:"+a);
       }
    }

}
