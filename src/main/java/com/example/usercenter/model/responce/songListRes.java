package com.example.usercenter.model.responce;

import lombok.Data;

import java.io.Serializable;


/**
 * 歌曲搜索返回体
 * @author tan
 */
@Data
public class songListRes implements Serializable {
    private static  final long serialVersionUID = 5404656853735985069L;
    private int code;
    Object data;
}
