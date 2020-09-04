package com.lizhimin.springbootvue.Landing;

import com.lizhimin.springbootvue.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GmallService {
    @Autowired
    RedisUtil redisUtil;

   public String checkToken(String token){
       String userName = "";
       Object tokenObject = redisUtil.hget("Login"+":"+token,token);
       if(tokenObject instanceof String){
           userName = (String)tokenObject;
       }
       return userName;
   }
}
