package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GmallService {
    @Autowired
    RedisUtil redisUtil;

    /**
     * 检查用户登录Token
     * @param token
     * @return 用户名
     */
   public String checkToken(String token){
       String userName = "";
       Object tokenObject = redisUtil.hget("Login:",token);
       if(tokenObject instanceof String){
           userName = (String)tokenObject;
       }
       return userName;
   }
   public long updateToken(String token,String username,String itemId){
       long currentTime = System.currentTimeMillis();
       Double time = turnLongToString(currentTime);

       //1、记录用户的token
       redisUtil.hset("Login:",token,username);
       //2、记录用户最后一次登录时间
       redisUtil.zset("recent:",token,Double.valueOf(time));
       //3、记录用户浏览过的商品
       redisUtil.zset("viewed"+":"+token,itemId,Double.valueOf(time));
       //4、移除旧记录，只保留最近浏览的25条记录
       redisUtil.zRemoveRange("viewed"+":"+token,0L,-26L);

       return 0L;
    }
    public Double turnLongToString(Long l){

        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        String format = nf.format(l);
        return Double.parseDouble(format);

    }

    /**
     * 添加到购物车
     * @param session 用户id
     * @param itemId 商品id
     * @param count 数量
     * @return 是否成功
     */
    public boolean addCart(String session,String itemId,Integer count){
       if(count<=0){
           return redisUtil.hrem("cart" + ":" + session, itemId)>0;
       }else{
           return redisUtil.hset("cart"+":"+session,itemId,count);
       }

    }



}
