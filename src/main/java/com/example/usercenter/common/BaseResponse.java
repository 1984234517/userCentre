package com.example.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T> 返回类的数据的类型
 * @author tan
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 4354622732340874204L;

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description){
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data){
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    public BaseResponse(ErrorCode errorCode, String message, String description){
        this(errorCode.getCode(), null, message, description);
    }

    public BaseResponse(int code, String message, String description){
        this(code, null, message, description);
    }
}
