package com.example.usercenter.constant;

/**
 * 用户常量
 * 
 * @author tan
 */
// 使用接口是因为接口成员变量值是public static的
public interface UserConstant {
    /**
     * 用户登陆态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    // ----- 权限 ------
    /**
     * 普通用户权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;
}
