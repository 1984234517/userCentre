package com.example.usercenter.model.responce;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录返回体
 * 
 * @author tan
 */
@Data
public class userLoginRes implements Serializable {
    private static final long serialVersionUID = 5404656853735985069L;
    private Long id;
    private String username;
    private String userAccount;
    private String avatarUrl;
    private Byte gender;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;
    private int role;
    private String planetCode;
}
