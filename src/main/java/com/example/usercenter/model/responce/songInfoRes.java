package com.example.usercenter.model.responce;

import lombok.Data;

import java.io.Serializable;

@Data
public class songInfoRes implements Serializable {
    private static final long serialVersionUID = 5404656853735985069L;
    private String songTitle;
    private String singerName;
    private String songUrl;
    private String lyric;
    private String songPic;
    private String songId;
}
