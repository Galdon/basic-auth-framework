package com.galdon.rrp.service.impl;

import com.galdon.rrp.dao.UserDao;
import com.galdon.rrp.entity.User;
import com.galdon.rrp.interceptor.TokenInterceptor;
import com.galdon.rrp.service.UserService;
import com.galdon.rrp.common.TokenGenerator;
import com.galdon.rrp.util.MD5Util;
import com.galdon.rrp.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.galdon.rrp.common.TokenGenerator.TOKEN_TYPE_ACCESS;

/**
 * @program: rrp
 * @description: 用户服务实现类
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public Map<String, Object> login(String username, String password) {
        User user = userDao.queryUserByUsername(username);
        if(user == null) {
            return null;
        }

        String encryptPw = user.getPassword();
        String inputPw = MD5Util.MD5(password, true);
        if(inputPw.equals(encryptPw)) {

            Map<String, Object> resultMap = new HashMap<String, Object>();
            user.setPassword(null);
            resultMap.put("user", user);

            // 检查是否存在 access_token
            if(redisUtil.hasKey(username + TokenGenerator.TOKEN_SUFFIX_ACCESS)) {
                resultMap.put(TokenInterceptor.ACCESS_TOKEN_HEADER, redisUtil.get(username + TokenGenerator.TOKEN_SUFFIX_ACCESS));
                resultMap.put(TokenInterceptor.REFRESH_TOKEN_HEADER, redisUtil.get(username + TokenGenerator.TOKEN_SUFFIX_REFRESH));

            // 重新生成 access_token 和 refresh_token
            } else {
                reGenTokens(username, resultMap);
            }

            return resultMap;
        }

        return null;
    }

    /**
     * 刷新 Token
      * @param accessToken
     * @param refreshToken
     * @return
     */
    @Override
    public Map<String, Object> refresh(String accessToken, String refreshToken) {
        // 解析用户名
        String username = String.valueOf(TokenGenerator.getClaimsFromToken(accessToken, TokenGenerator.TOKEN_TYPE_ACCESS).get(TokenGenerator.CLAIMS_USERNAME));

        // 检查是否存在 refresh_token
        if(redisUtil.hasKey(username + TokenGenerator.TOKEN_SUFFIX_REFRESH)) {
            // 重新生成 accessToken 和 refreshToken
            Map<String, Object> resultMap = new HashMap<String, Object>();
            reGenTokens(username, resultMap);
            return resultMap;
        } else {
            return null;
        }
    }

    /**
     * 重新生成 access_token 和 refresh_token
     * @param username
     * @param resultMap
     */
    private void reGenTokens(String username, Map<String, Object> resultMap) {
        String accessToken = TokenGenerator.generateToken(username, TokenGenerator.TOKEN_TYPE_ACCESS);
        String refreshToken = TokenGenerator.generateToken(username, TokenGenerator.TOKEN_TYPE_REFRESH);
        redisUtil.set(username + TokenGenerator.TOKEN_SUFFIX_ACCESS, accessToken, TokenGenerator.ACCESS_EXPIRE);
        redisUtil.set(username + TokenGenerator.TOKEN_SUFFIX_REFRESH, refreshToken, TokenGenerator.REFRESH_EXPIRE);
        resultMap.put(TokenInterceptor.ACCESS_TOKEN_HEADER, accessToken);
        resultMap.put(TokenInterceptor.REFRESH_TOKEN_HEADER, refreshToken);
    }

    /**
     * 用户登出
     * @param token
     */
    @Override
    public void logout(String token) {
        String username = String.valueOf(TokenGenerator.getClaimsFromToken(token, TokenGenerator.TOKEN_TYPE_ACCESS).get(TokenGenerator.CLAIMS_USERNAME));
        redisUtil.del(username + TokenGenerator.TOKEN_SUFFIX_REFRESH);
        redisUtil.del(username + TokenGenerator.TOKEN_SUFFIX_ACCESS);
    }

    /**
     * 获取当前登录人
     * @param token
     * @return
     */
    @Override
    public User getLoginUser(String token) {
        String username = String.valueOf(TokenGenerator.getClaimsFromToken(token, TOKEN_TYPE_ACCESS).get(TokenGenerator.CLAIMS_USERNAME));
        return userDao.queryUserByUsername(username);
    }

    /**
     * 获取所有用户
     * @return
     */
    @Override
    public List<User> queryAllUser() {
        return userDao.queryAllUser();
    }
}
