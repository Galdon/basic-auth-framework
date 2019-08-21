package com.galdon.rrp.util;

/**
 * @program: rrp
 * @description: 字符串工具类
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0 || "null".equals(str);
    }

    /**
     * 判断字符串非空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
