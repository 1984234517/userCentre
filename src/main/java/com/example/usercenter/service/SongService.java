package com.example.usercenter.service;

import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.model.responce.songInfoRes;

import java.io.IOException;
import java.util.List;

public interface SongService {
    List<songInfoRes> getSongList(String type, String keyWords) throws IOException;
}
