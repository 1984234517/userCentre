package com.example.usercenter.service.impl;

import com.example.usercenter.constant.HttpUtil;
import com.example.usercenter.constant.JsonUtils;
import com.example.usercenter.constant.SongConstant;
import com.example.usercenter.model.responce.songInfoRes;
import com.example.usercenter.model.responce.songListRes;
import com.example.usercenter.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class SongServiceImpl implements SongService {

    @Override
    public List<songInfoRes> getSongList(String type, String keyWords) throws IOException {
        String url = SongConstant.BASEURL + type + "&query_value=" + keyWords;
        String res = HttpUtil.sendGet(url);
        List<songInfoRes> songList = new ArrayList<>();
        // 先将其转换成code+data
        songListRes result = JsonUtils.jsonToBean(res, songListRes.class);
        if (result.getCode() == 0){
            songList = JsonUtils.jsonToBeanList(result.getData().toString(), songInfoRes.class);
        }
        return songList;
    }
}
