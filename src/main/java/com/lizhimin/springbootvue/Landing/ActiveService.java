
package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public boolean articleVote(String activeId,String userId) {


        //1、查看文章是否发表是否已经超过一周
        Object time = redisUtil.zget("time", "active：" + activeId);
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        String format = nf.format(time);
        /**
         * 2.Long.ValueOf(“String”)与Long.parseLong(“String”)的区别
         *
         * Long.ValueOf(“String”)返回Long包装类型
         *
         * Long.parseLong(“String”)返回long基本数据类型
         */
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


}
