
package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.entity.ActiveBO;
import com.lizhimin.springbootvue.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
        Object time = redisUtil.zget("time", "active"+":" + activeId);
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
        String voted ="voted"+ ":" + activeId;
        if (redisUtil.sSet(voted,userId) == 1) {
            //用户以前没有点赞过

            //4、文章的score分值增加432（常量）
            redisUtil.zincrby("score", "active"+":" + activeId, VOTE_SCORE);

            //5、用户active：1中 votes 的数值+1
            redisUtil.hincr( "active"+":" + activeId, "votes", 1);

        }
        return true;
    }

    /**
     * 发布文章
     * @param activeBO 文章po
     * @return boolean
     */
    public boolean postActive(ActiveBO activeBO){
        long l = System.currentTimeMillis();

        //发布文章
        //1、生成activeId
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String activeId = simpleDateFormat.format(new Date())+activeBO.getPoster().substring(6);

        //2、把activeId 放在 voted（投票用户的集合）中 设置过期时间为1个星期
        String voted ="voted"+ ":" + activeId;
        redisUtil.sSet(voted,activeBO.getPoster());
        redisUtil.expire(voted,WEEK_SENDS);

        //3、把文章信息放在散列中
        String active = "active"+":" + activeId;
        Map<String,Object> map = new HashMap<>();
        map.put("title",activeBO.getTitle());
        map.put("link",activeBO.getLink());
        map.put("poster",activeBO.getPoster());
        map.put("time",l);
        map.put("votes",activeBO.getVotes());
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

    /**
     * 获取最新文章id集合
     * @param min 时间start
     * @param max 时间end
     * @return 获取最新文章id集合
     */
    public List<Map> getActivesByCreatetime(Double min,Double max){
        //1、从time的有序集合中获取最新创建的前三条数据
        List<Map> activesList = new ArrayList();
        List<String> activeIdList = new ArrayList<>();
        Set<Object> activeIdsList = redisUtil.zrangeByScorce("time", min, max);
        for (Object object:activeIdsList) {
            if(object instanceof String){
                activeIdList.add((String)object);
            }else{
                System.out.println("转化失败");
                return null;
            }
        }
        for (String activeId:activeIdList) {
            Map<Object, Object> activeInformation = redisUtil.hmget(activeId);
            activesList.add(activeInformation);
        }
        return activesList;


    }
    /**
     * 根据分数获取点赞最多的
     * @param min 点赞
     * @param max 数字
     * @return 获取最新文章id集合
     */
    public List<Map> getActivesByScore(Double min,Double max){
        //1、从time的有序集合中获取最新创建的前三条数据
        List<Map> activesList = new ArrayList();
        List<String> activeIdList = new ArrayList<>();
        Set<Object> activeList = redisUtil.zrangeByScorce("score", min, max);
        for (Object object:activeList) {
            if(object instanceof String){
                activeIdList.add((String)object);
            }else{
                System.out.println("转化失败");
                return null;
            }
        }
        for (String activeId:activeIdList) {
            Map<Object, Object> activeInformation = redisUtil.hmget(activeId);
            activesList.add(activeInformation);
        }
        return activesList;

    }

    /**
     * 添加activeId到分组
     * @param activeId
     * @return 是否成功
     */
    public Long addGroups(String activeId,String groupName){
       return redisUtil.sSet("Groups"+":"+groupName,"active:"+activeId);
    }

    /**
     * 移除分组中的activeId
     * @param activeId
     * @return 是否成功
     */
    public Long delGroup(String activeId,String groupName){
        if(redisUtil.sIsMember("Groups"+":"+groupName,"active:"+activeId) == true){
           return redisUtil.setRemove("Groups"+":"+groupName,"active:"+activeId);
        }
        return 0L;
    }

}
