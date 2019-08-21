package com.galdon.rrp.service;

import com.galdon.rrp.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @program: rrp
 * @description: 用户服务类
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
public interface UserService {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    Map<String, Object> login(String username, String password);

    /**
     * 刷新 Token
     * @param accessToken
     * @param refreshToken
     * @return
     */
    Map<String, Object> refresh(String accessToken, String refreshToken);

    /**
     * 用户登出
     * @param token
     */
    void logout(String token);

    /**
     * 获取当前登录人信息
     * @param token
     * @return
     */
    User getLoginUser(String token);

    /**
     * 获取所有用户
     * @return
     */
    List<User> queryAllUser();

}
