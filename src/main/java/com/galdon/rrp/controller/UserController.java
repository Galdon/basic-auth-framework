package com.galdon.rrp.controller;

import com.galdon.rrp.common.*;
import com.galdon.rrp.entity.User;
import com.galdon.rrp.interceptor.TokenInterceptor;
import com.galdon.rrp.service.UserService;
import com.galdon.rrp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rrp
 * @description: 用户控制器
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public BaseResult<Map<String, Object>> login(String username, String password) {
        Map<String, Object> resultMap = userService.login(username, password);
        if(resultMap != null) {
            return ResultUtil.success(resultMap);
        } else {
            return ResultUtil.error(ResEnum.LOGIN_ERROR);
        }
    }

    /**
     * 刷新 token
     * @param accessToken
     * @param refreshToken
     * @return
     */
    @RequestMapping("/refresh")
    public BaseResult<Map<String, Object>> refresh(String accessToken, String refreshToken) {
        Map<String, Object> tokens = userService.refresh(accessToken, refreshToken);
        if(tokens == null) {
            throw new BaseException(ResEnum.TOKEN_REFRESH_FAILED);
        }
        return ResultUtil.success(tokens);
    }

    /**
     * 用户登出
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public BaseResult<Object> logout(HttpServletRequest request) {
        String token = String.valueOf(request.getAttribute(TokenInterceptor.ACCESS_TOKEN_HEADER));
        userService.logout(token);
        return ResultUtil.success(null);
    }


    /**
     * 获取所有用户
     * 可测试接口，需要 token 验证
     * @return
     */
    @RequestMapping("/allUser")
    public BaseResult<List<User>> queryAllUser(HttpServletRequest request) {
        User currentUser = userService.getLoginUser(String.valueOf(request.getAttribute(TokenInterceptor.ACCESS_TOKEN_HEADER)));
        List<User> users = userService.queryAllUser();
        return ResultUtil.success(users);
    }

}
