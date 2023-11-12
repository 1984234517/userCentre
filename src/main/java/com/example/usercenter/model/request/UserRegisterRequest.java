package com.example.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * 
 * @author tan
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 688845076365353633L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
