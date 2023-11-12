package com.example.usercenter.controller;

import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.common.ResultUtils;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.model.request.UserDeleteRequest;
import com.example.usercenter.model.request.UserLoginRequest;
import com.example.usercenter.model.request.UserRegisterRequest;
import com.example.usercenter.model.responce.userLoginRes;
import com.example.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.usercenter.common.ErrorCode.*;
import static com.example.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class userController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(PARAMS_ERROR, "接收参数失败");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(PARAMS_ERROR, "用户密码不能为空");
        }
        long id = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(id);
    }

    @CrossOrigin
    @PostMapping("/login")
    public BaseResponse<userLoginRes> userLogin(@RequestBody UserLoginRequest userLoginRequest,
        HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(PARAMS_ERROR, "参数接收失败");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(PARAMS_ERROR, "用户密码不能为空");
        }
        userLoginRes user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @GetMapping("/search")
    public BaseResponse<List<userLoginRes>> searchUsers(String username, String planetCode,
        HttpServletRequest request) {
        if (Boolean.FALSE.equals(isAdmin(request))) {
            throw new BusinessException(NO_AUTH, "无查询权限");
        }
        List<userLoginRes> userList = userService.searchUses(username, planetCode);
        return ResultUtils.success(userList);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeleteRequest userDeleteRequest,
        HttpServletRequest request) {
        if (userDeleteRequest.getId() <= 0 || Boolean.TRUE.equals(!isAdmin(request))) {
            throw new BusinessException(NO_AUTH, "无删除权限");
        }
        // 由于我们使用Mybatis-plus的逻辑删除，所以这里删除会转变为更新
        Boolean res = userService.deleteUser(userDeleteRequest.getId());
        return ResultUtils.success(res);
    }

    /**
     * 当前用户是否为管理员
     * 
     * @param request 请求参数
     * @return 当前用户为管理员返回true，否则返回false;
     */
    private Boolean isAdmin(HttpServletRequest request) {
        // 鉴权
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        userLoginRes user = (userLoginRes)userObject;
        return user != null && user.getRole() == ADMIN_ROLE;
    }

    @GetMapping("/current")
    public BaseResponse<userLoginRes> getCurrent(HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)userObject;
        if (user == null) {
            throw new BusinessException(NOT_LOGIN);
        }
        // todo 後期需要驗證用戶的狀態，比如是否處於封號狀態等
        userLoginRes currentUser = userService.getCurrent(user.getId());
        return ResultUtils.success(currentUser);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLoginOut(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(NOT_LOGIN);
        }
        int status = userService.userLoginOut(request);
        return ResultUtils.success(status);
    }
}
