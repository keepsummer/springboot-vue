package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
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
    @Autowired
    RedisTemplate redisTemplate;

    private static final Long TEN_MINUTES = 600L;
    /** HashSet
     * 添加用户信息
     */
    public Boolean addUserInfo(String userId, String name, BigDecimal funds){
        Map<String,Object> userInfoMap = new HashMap<>(10);
        userInfoMap.put("name",name);
        userInfoMap.put("funds",funds);
        return redisUtil.hmset("user:"+userId,userInfoMap);
    }

    /** set
     *  给用户背包中添加商品
     * @return 成功
     */
    public Long addInventory(String userId,String itemId){
      return redisUtil.sSet("inventory"+":"+userId,itemId);
    }

    /**
     * 添加商品到市场中去
     * @param itemId 商品id
     * @param userId 用户id
     * @param price 价格
     * @return 成功
     */
    public Boolean addItemInfo(String itemId,String userId,Double price){

        return redisUtil.zset("market:",itemId+":"+userId,price);
    }
    /**
     * 添加商品到市场中去 加事务，
     * @param itemId 商品id
     * @param userId 用户id
     * @param price 价格
     * @return  成功
     */
    public Boolean addItemInfoToMarket(String itemId,String userId,Double price){
        //1、定义：inventory
        String inventory = "inventory"+":"+userId;
        //获取当前时间
        long l = System.currentTimeMillis();
        while (System.currentTimeMillis()-l <= TEN_MINUTES){
            //监视用户背包发生变化
            redisTemplate.watch(inventory);
            //检查用户是否仍然持有被销售商品
            boolean b = redisUtil.sIsMember(inventory, itemId);
            if(!b){
                //如果5秒后不存在了就不监视了
                redisTemplate.unwatch();
            }
            redisTemplate.execute(new SessionCallback() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {
                    operations.opsForZSet().add("market:",itemId+":"+userId,price);
                    operations.opsForSet().remove(inventory, itemId);
                    return null;
                }
            });
            return true;
        }
        return false;

    }

}
