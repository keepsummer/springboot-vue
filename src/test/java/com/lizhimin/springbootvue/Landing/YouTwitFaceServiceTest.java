package com.lizhimin.springbootvue.Landing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class YouTwitFaceServiceTest {
    @Autowired
    YouTwitFaceService youTwitFaceService;
    @Test
    public void testAddUserInfo(){
        BigDecimal bigDecimal = new BigDecimal(125D);
       // youTwitFaceService.addUserInfo("10","Frank",bigDecimal);
        youTwitFaceService.addUserInfo("11","Bill",bigDecimal);
    }
    @Test
    public void testaddInvenrtoryInfo(){
        youTwitFaceService.addInventory("10","item1001");
        youTwitFaceService.addInventory("11","item1101");
    }

}