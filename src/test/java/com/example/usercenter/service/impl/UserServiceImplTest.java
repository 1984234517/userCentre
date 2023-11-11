package com.example.usercenter.service.impl;

import com.example.usercenter.model.domain.User;
import com.example.usercenter.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户服务测试
 * @author tan
 */

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("test");
        user.setUserAccount("123");
        user.setAvatarUrl("https://bpic.588ku.com/element_origin_min_pic/19/06/15/199987770a5a52002b63707a4f5f2f24.jpg");
        user.setGender((byte) 0);
        user.setUserPassword("123");
        user.setPhone("1234567890");
        user.setEmail("1234566");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
//        String userAccount = "tanx@";
//        String userPassword = "";
//        String checkPassword = "123456";
//        String planetCode = "1";
//        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
//        Assertions.assertEquals(-1,result);
//
//        userPassword = "123456";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
//        Assertions.assertEquals(-1,result);
//
//        userAccount = "tanx";
//        userPassword = "123456@";
//        checkPassword = "123456@";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
//        Assertions.assertEquals(-1,result);
//
//        userAccount = "tanx@";
//        userPassword = "12345678@";
//        checkPassword = "12345678@";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
//        Assert.assertEquals(-1,result);
//        userAccount = "tanx";
//        userPassword = "12345678";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
//        Assert.assertEquals(-1,result);
//        userAccount = "tianxin";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
//        System.out.println(result);
//        Assertions.assertTrue(result>0);

    }
}