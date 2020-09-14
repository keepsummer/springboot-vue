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
        youTwitFaceService.addInventory("10","itemA");
        youTwitFaceService.addInventory("11","itemB");
        youTwitFaceService.addInventory("10","itemC");
        youTwitFaceService.addInventory("11","itemD");
        youTwitFaceService.addInventory("10","itemE");
        youTwitFaceService.addInventory("11","itemF");
        youTwitFaceService.addInventory("10","itemI");
        youTwitFaceService.addInventory("11","itemG");
    }
    @Test
    public void testaddItemInfo(){
        youTwitFaceService.addItemInfo("itemA","10",15D);
        youTwitFaceService.addItemInfo("itemB","11",45D);
        youTwitFaceService.addItemInfo("itemC","10",16D);
        youTwitFaceService.addItemInfo("itemD","11",29D);


    }

}