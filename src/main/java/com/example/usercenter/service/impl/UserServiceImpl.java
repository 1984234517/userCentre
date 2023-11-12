package com.example.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.model.responce.userLoginRes;
import com.example.usercenter.service.UserService;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;
import static com.example.usercenter.common.ErrorCode.*;

/**
 * 用户服务实现类
 * 
 * @author tan
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 盐值
     */
    private static final String SALT = "abcd";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(PARAMS_ERROR, "参数不能为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(PARAMS_ERROR, "用户账号长度过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(PARAMS_ERROR, "用户密码过短");
        }
        if (planetCode.length() > 5) {
            throw new BusinessException(PARAMS_ERROR, "星球编号长度过长");
        }
        // 账号不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\[\\]_ \\n]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(PARAMS_ERROR, "用户账号不能含有特殊字符");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(PARAMS_ERROR, "两次密码不一致");
        }
        // 账号不能重名
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(PARAMS_ERROR, "账号已被注册");
        }

        // 星球编号不能重复
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(PARAMS_ERROR, "星球编号已被注册");
        }
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean save = this.save(user);
        if (!save)
            throw new BusinessException(REGISTER_FAIL);
        return user.getId();
    }

    @Override
    public userLoginRes userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            // todo 修改为自定义异常类
            throw new BusinessException(PARAMS_ERROR, "账号不能为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(PARAMS_ERROR, "账号长度过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(PARAMS_ERROR, "密码过短");
        }
        // 账号不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\[\\]_ \\n]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(PARAMS_ERROR, "账号含有非法字符");
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount or userPassword cannot match");
            throw new BusinessException(USER_NOT_EXIT, "用户名或者密码不正确");
        }
        userLoginRes safetyUser = getSafetyUser(user);
        // 记录用户登陆状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public userLoginRes getSafetyUser(User originUser) {
        // 用户脱敏
        if (originUser == null) {
            throw new BusinessException(USER_NOT_EXIT);
        }
        userLoginRes safetyUser = new userLoginRes();
        BeanUtils.copyProperties(safetyUser, originUser);
        return safetyUser;
    }

    @Override
    public List<userLoginRes> searchUses(String username, String planetCode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(planetCode)) {
            queryWrapper.like("username", username).eq("planetCode", planetCode);
        } else if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        } else if (StringUtils.isNotBlank(planetCode)) {
            queryWrapper.eq("planetCode", planetCode);
        }
        List<userLoginRes> res = new ArrayList<>();
        List<User> users = userMapper.selectList(queryWrapper);
        for (int i = 0; i < users.size(); i++) {
            res.add(getSafetyUser(users.get(i)));
        }
        return res;
    }

    @Override
    public Boolean deleteUser(long id) {
        int result = userMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public userLoginRes getCurrent(Long id) {
        return getSafetyUser(userMapper.selectById(id));
    }

    @Override
    public int userLoginOut(HttpServletRequest request) {
        // 移除登陆态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}
