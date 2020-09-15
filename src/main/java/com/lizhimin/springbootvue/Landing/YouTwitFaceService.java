package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import sun.awt.windows.ThemeReader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static final Long SIX_SECONDS = 6000L;
    private static final String MARKET = "market:";
    /** HashSet
     * 添加用户信息
     */
    public Boolean addUserInfo(String userId, String name, Double funds){
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
        while (System.currentTimeMillis()-l <= SIX_SECONDS){
            //监视用户背包发生变化
            redisTemplate.watch(inventory);
            //检查用户是否仍然持有被销售商品
            boolean b = redisUtil.sIsMember(inventory, itemId);
            if(!b){
                //如果不存在了就不监视了
                System.out.println("键不存在了");
                redisTemplate.unwatch();
                return null;
            }

            redisTemplate.execute(new SessionCallback() {
                @Override
                public Object execute(RedisOperations operations) throws DataAccessException {
                    operations.multi();
                    System.out.println("事务开始");
                    operations.opsForZSet().add("market:",itemId+":"+userId,price);
                    operations.opsForSet().remove(inventory, itemId);
                    System.out.println("itemId: "+itemId);
                    System.out.println("price: "+price);
                    operations.exec();
                    return null;
                }
            });
        }
        return true;

    }
    public boolean purchaseItem(String buyerId,String sellerId,String itemId,Double price){
        //使用watch对市场和个人信息进行监视
        //2、获取买家拥有的钱和商品的售价
        //3、检查买家的钱是否足够
        //4、扣减买家的钱，增加卖家的钱，买家的背包增加该商品，市场上减少该商品
        String buyer = "user"+":"+buyerId;
        String seller = "user"+":"+sellerId;
        String item = itemId + ":" + sellerId;
        String inventory = "inventory"+":"+buyerId;
        List<String> keyList = new ArrayList<>();
        keyList.add("market:");
        keyList.add(buyer);

            long l = System.currentTimeMillis();
            while (System.currentTimeMillis()-l <= SIX_SECONDS){
                try {
                    redisTemplate.watch(keyList);
                    //获取商品的售价
                    Double price1 = redisUtil.zscore("market:",item);
                    Object funds = redisUtil.hget(buyer, "funds");
                    if(funds instanceof BigDecimal){
                        BigDecimal funds1= (BigDecimal) funds;
                        if(funds1.doubleValue()< price && !price.equals(price1)){
                            redisTemplate.unwatch();
                            return false;
                        }
                        redisTemplate.execute(new SessionCallback() {
                          @Override
                          public Object execute(RedisOperations operations) throws DataAccessException {
                              operations.multi();
                              //先将买家的钱支付给买家，
                              operations.opsForHash().increment(buyer,"funds",-price);
                              operations.opsForHash().increment(seller,"funds",price);
                              operations.opsForSet().add(inventory,itemId);
                              operations.opsForZSet().remove("market:",item);
                              operations.exec();
                              return true;
                          }
                      });

                    }
                }catch (Exception e){
                    System.out.println("出现异常");
                    continue;
                }
            }

        return false;

    }

}
