package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *一个虚拟社交网站，用户销售商品
 */
@Service
public class YouTwitFaceService {
    @Autowired
    RedisUtil redisUtil;

    /** HashSet
     * 添加用户信息
     */
    public Boolean addUserInfo(String userId, String name, BigDecimal funds){
        Map<String,Object> userInfoMap = new HashMap<>();
        userInfoMap.put("name",name);
        userInfoMap.put("funds",funds);
        return redisUtil.hmset("user:"+userId,userInfoMap);
    }

    /** set
     *  给用户背包中添加商品
     * @return
     */
    public Long addInventory(String userId,String itemId){
      return redisUtil.sSet("inventory"+":"+userId,itemId);
    }

    /**
     * 添加商品到市场中去
     * @param itemId
     * @param userId
     * @param price
     * @return
     */
    public Boolean addItemInfo(String itemId,String userId,Double price){
        return redisUtil.zset("market:",itemId+":"+userId,price);
    }

}
