
package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.entity.Active;
import com.lizhimin.springbootvue.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActiveService {
    /**
     * 逻辑梳理
     * 1、如果一篇文章获得支持票》=200张就放在前面
     *
     */
    private static final Long WEEK_SENDS = 7 * 86400L;
    private static final Long VOTE_SCORE = 432L;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 对文章进行投票
     * @param activeId 文章id
     * @param userId 用户id
     * @return boolean 是否成功
     */
    public boolean articleVote(String activeId,String userId) {


        //1、查看文章是否发表是否已经超过一周
        Object time = redisUtil.zget("time", "active：" + activeId);
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        String format = nf.format(time);
        //2.Long.ValueOf(“String”)与Long.parseLong(“String”)的区别
         //Long.ValueOf(“String”)返回Long包装类型
         //Long.parseLong(“String”)返回long基本数据类型

        if (System.currentTimeMillis() - Long.parseLong(format) < WEEK_SENDS) {
            return false;
        }

        // 2、用户点击支持，判断该用户时候已经点击过  //3、把用户放在voted中，
        if (redisUtil.sSet("voted：" + activeId, userId) == 1) {
            //用户以前没有点赞过

            //4、文章的score分值增加432（常量）
            redisUtil.zincrby("score", "active：" + activeId, VOTE_SCORE);

            //5、用户active：1中 votes 的数值+1
            redisUtil.hincr("active：" + activeId, "votes", 1);

        }
        return true;
    }

    /**
     * 发布文章
     * @param activePO 文章po
     * @return boolean
     */
    public boolean postActive(Active activePO){
        long l = System.currentTimeMillis();

        //发布文章
        //1、生成activeId
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String activeId = simpleDateFormat.format(new Date())+activePO.getPoster().substring(6);

        //2、把activeId 放在 voted（投票用户的集合）中 设置过期时间为1个星期
        String voted ="voted"+ ":" + activeId;
        redisUtil.sSet(voted,activePO.getPoster());
        redisUtil.expire(voted,WEEK_SENDS);

        //3、把文章信息放在散列中
        String active = "active"+":" + activeId;
        Map<String,Object> map = new HashMap<>();
        map.put("title",activePO.getTitle());
        map.put("link",activePO.getLink());
        map.put("poster",activePO.getPoster());
        map.put("time",l);
        map.put("votes",activePO.getVotes());
        redisUtil.hmset(active,map);
        //4、发布时间、发布初始分数 设置到有序集合中
        redisUtil.zset("score","active"+":" + activeId,0D);
        Double time = turnLongToString(l);
        redisUtil.zset("time", "active"+":" + activeId,time);
        return true;
    }
    public Double turnLongToString(Long l){

        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        String format = nf.format(l);
        return Double.parseDouble(format);

    }

}
