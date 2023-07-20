package com.example.usercenter.controller;


import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.common.ResultUtils;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.model.responce.songInfoRes;
import com.example.usercenter.model.responce.songListRes;
import com.example.usercenter.model.responce.userLoginRes;
import com.example.usercenter.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static com.example.usercenter.common.ErrorCode.NOT_LOGIN;
import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/song")
public class songController {
    @Resource
    private SongService songService;

    @GetMapping("/search")
    public BaseResponse<List<songInfoRes>> getSongList(String type, String keyWords, HttpServletRequest request) throws IOException {
        // 检查是否登录
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        userLoginRes user = (userLoginRes) userObject;
        if (user == null){
            throw  new BusinessException(NOT_LOGIN);
        }
        List<songInfoRes> songList = songService.getSongList(type, keyWords);
        return ResultUtils.success(songList);
    }
}
