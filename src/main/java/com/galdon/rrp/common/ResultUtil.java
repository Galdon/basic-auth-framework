package com.galdon.rrp.common;

/**
 * @program: rrp
 * @description: 统一返回工具类
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
public class ResultUtil {

    /**
     * 操作成功返回模板
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> success(T data) {
        return success(data, ResEnum.SUCCESS);
    }

    /**
     * 操作失败返回模板1
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> error() {
        return error(ResEnum.UNKNOWN_ERROR);
    }

    /**
     * 操作失败返回模板2
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> error(String msg) {
        return error(200, msg);
    }

    /**
     * 操作失败返回模板3
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> error(Integer code, String msg) {
        return commonResult(0, code, msg, null);
    }

    public static <T> BaseResult<T> error(ResEnum resEnum) {
        return commonResult(0, resEnum.getCode(), resEnum.getMsg(), null);
    }

    private static <T> BaseResult<T> success(T data, ResEnum resEnum) {
        return commonResult(1, resEnum.getCode(), resEnum.getMsg(), data);
    }

    /**
     * 私有数据组装方法
     * @param status
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    private static <T> BaseResult<T> commonResult(Integer status, Integer code, String msg, T data) {
        BaseResult<T> result = new BaseResult<>();
        result.setStatus(status);
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
