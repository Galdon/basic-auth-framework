package com.galdon.rrp.common;

/**
 * @program: rrp
 * @description: 统一返回实体
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
public class BaseResult<T> {

    /* 自定义返回码 */
    private Integer code;

    /* 操作成功标识 0-失败 1-成功 */
    private Integer status;

    /* 提示信息 */
    private String msg;

    /* 操作返回数据 */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
