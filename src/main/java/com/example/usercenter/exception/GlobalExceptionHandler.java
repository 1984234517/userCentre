package com.example.usercenter.exception;

import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.common.ErrorCode;
import com.example.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.usercenter.common.ErrorCode.*;

/**
 * 全局异常处理器
 *
 * @author tan
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("businessException"+e.getMessage(), e);

        System.out.println("成功捕获");
        return ResultUtils.error(e.getCode(), e.getMessage(),e.getDescription());
    }

//    @ExceptionHandler(RuntimeException.class)
//    public BaseResponse runtimeExceptionHandler(RuntimeException e){
//        log.error("runtimeException", e);
//        return ResultUtils.error(SYSTEM_ERROR, e.getMessage(),"");
//    }
}
