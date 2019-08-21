package com.galdon.rrp.common;

/**
 * @program: rrp
 * @description: 统一异常类
 * @author: galdon
 * @create: 2019-08-15 20:22
 **/
public class BaseException extends RuntimeException {

    private String errorMsg;
    private Integer code;

    public BaseException(Integer code, String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.code = code;
    }

    public BaseException(ResEnum resEnum) {
        super(resEnum.getMsg());
        this.errorMsg = resEnum.getMsg();
        this.code = resEnum.getCode();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Integer getCode() {
        return code;
    }

}
