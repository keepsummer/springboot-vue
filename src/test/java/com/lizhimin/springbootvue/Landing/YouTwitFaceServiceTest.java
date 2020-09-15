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
       youTwitFaceService.addUserInfo("10","Frank",101D);
        youTwitFaceService.addUserInfo("11","Bill",126D);
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
    @Test
    public void testaddItemInfo1(){
      /// youTwitFaceService.addItemInfoToMarket("itemE","10",21D);
        youTwitFaceService.addItemInfoToMarket("itemA","10",12D);
    }
    @Test
    public void testaddItemInfo2(){
        /// youTwitFaceService.addItemInfoToMarket("itemE","10",21D);
        youTwitFaceService.addItemInfoToMarket("itemA","10",32D);
    }
    @Test
    public void testpurchaseItem(){
        boolean b = youTwitFaceService.purchaseItem("11", "10", "itemA", 12D);

    }

}