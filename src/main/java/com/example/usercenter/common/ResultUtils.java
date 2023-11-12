package com.example.usercenter.common;

/**
 * 返回工具类
 * 
 * @author tan
 */
public class ResultUtils {

    /**
     * 请求成功
     * 
     * @param data 请求成功返回数据
     * @param <T> 返回的数据请求体的类型
     * @return 请求成功返回体
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "0k", "");
    }

    /**
     * 请求失败
     * 
     * @param errorCode 请求失败枚举类
     * @return 请求失败返回体
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse(errorCode);
    }

    /**
     * 请求失败
     * 
     * @param errorCode 请求失败枚举类
     * @return 请求失败返回体
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode, message, description);
    }

    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse(code, message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode, errorCode.getMessage(), description);
    }

}
