package com.example.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.model.responce.userLoginRes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *用户接口
 * @author tan
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param planetCode 星球编号
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登陆
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param request 用户请求
     * @return 用户信息
     */
    userLoginRes userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户信息脱敏
     * @param originUser 原始用户对象
     * @return 信息脱敏后的用户对象
     */
    userLoginRes getSafetyUser(User originUser);

    /**
     * 用户查询
     * @param username 用户名
     * @return 满足条件的用户列表
     */
    List<userLoginRes> searchUses(String username, String planetCode);

    /**
     * 用户删除
     * @param id 用户id
     * @return 是否成功删除
     */
    Boolean deleteUser(long id);

    /**
     * 查詢當前用戶
     * @param id 用戶id
     * @return 當前用戶信息
     */
    userLoginRes getCurrent(Long id);


    /**
     * 用户注销
     * @param request cookie等信息
     * @return 是否成功注销
     */
    int userLoginOut(HttpServletRequest request);
}
