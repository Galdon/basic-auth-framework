package com.galdon.rrp.common;

/**
 * @program: rrp
 * @description: 返回码枚举
 * @author: galdon
 * @create: 2019-08-15 20:22
 **/
public enum ResEnum {

    // 通用成功
    SUCCESS(100, "操作成功"),

    // 通用失败
    UNKNOWN_ERROR(200, "服务器内部错误，请联系管理员"),

    // 3XX 用户相关失败
    LOGIN_ERROR(300, "用户名或密码错误"),
    USER_NOT_AVAILABLE(301, "用户不可用"),
    TOKEN_INVALID(302, "无效的TOKEN"),
    TOKEN_EXPIRED(303, "TOKEN已过期"),
    NO_TOKEN(304, "没有验证所需的TOKEN"),
    TOKEN_REFRESH_FAILED(305, "TOKEN刷新失败，请重新登录"),
    ;

    private Integer code;
    private String msg;

    private ResEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
