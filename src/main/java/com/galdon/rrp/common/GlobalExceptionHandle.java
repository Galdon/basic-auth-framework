package com.galdon.rrp.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: rrp
 * @description: 统一异常处理类
 * @author: galdon
 * @create: 2019-08-15 20:25
 **/
@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public <T> BaseResult<T> handle(Exception e) {
        if(e instanceof BaseException) {
            BaseException exception = (BaseException) e;
            Integer code = exception.getCode();
            return ResultUtil.error(code, e.getMessage());
        }

        e.printStackTrace();
        return ResultUtil.error(ResEnum.UNKNOWN_ERROR);
    }
}
