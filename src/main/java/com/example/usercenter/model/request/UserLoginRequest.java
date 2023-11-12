package com.example.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登陆请求体
 * 
 * @author tan
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 5404656853735985069L;

    private String userAccount;

    private String userPassword;
}
