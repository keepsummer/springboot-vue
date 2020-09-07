package com.lizhimin.springbootvue.listener;

import com.lizhimin.springbootvue.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import java.util.Set;

import static java.lang.Long.min;

/**
 * 定时删除用户收听过的poi
 * @author wenbronk
 */
@Component
@Configurable
@EnableScheduling
public class DelListenerPOIScheduler {
    private final Logger LOGGER = LoggerFactory.getLogger(DelListenerPOIScheduler.class);
    public static final Integer LIMIT = 10;

    @Autowired
    RedisUtil redisUtil;


    public void doCheck() {

        try {

            Long size = redisUtil.zCard("recent:");
            if(size>LIMIT){
                long min = min(size - LIMIT, 5L);
                Set<Object> tokens = redisUtil.zRange("recent:", 0L, min - 1);
                for (Object token:tokens) {
                    if(token instanceof String){
                        redisUtil.del("viewed"+":"+token);
                        redisUtil.hdel("Login",token);
                        redisUtil.zRemove("recent:",token);
                        //清除用户购物车数据
                        redisUtil.del("cart"+":"+token);


                        LOGGER.info("Token={}",token);

                    }
                }
                //把viewed序列中前200名的商品留下，其余删除 ，最受欢迎商品
                redisUtil.zRemoveRange("viewed" + ":", 0L, -201L);
            }
            LOGGER.info("redis中旧令牌被清除");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("redis旧令牌清除失败");
        }
    }

}