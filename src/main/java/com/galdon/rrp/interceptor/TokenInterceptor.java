package com.galdon.rrp.interceptor;

import com.galdon.rrp.common.BaseException;
import com.galdon.rrp.common.ResEnum;
import com.galdon.rrp.common.TokenGenerator;
import com.galdon.rrp.util.RedisUtil;
import com.galdon.rrp.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: rrp
 * @description: 自定义 Token 拦截器
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
public class TokenInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    public static final String ACCESS_TOKEN_HEADER = "access_token";
    public static final String REFRESH_TOKEN_HEADER = "refresh_token";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取 header 中的 access_token
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        if(StringUtil.isEmpty(accessToken)) {
            throw new BaseException(ResEnum.NO_TOKEN);
        }

        // 获取 access_token 中的用户名，与 redis 中的比较
        String username = String.valueOf(TokenGenerator.getClaimsFromToken(accessToken, TokenGenerator.TOKEN_TYPE_ACCESS).get(TokenGenerator.CLAIMS_USERNAME));

        // 与 redis 中的 access_token 比较
        String accessTokenFormRedis = String.valueOf(redisUtil.get(username + TokenGenerator.TOKEN_SUFFIX_ACCESS));
        if(StringUtil.isNotEmpty(accessTokenFormRedis) && accessTokenFormRedis.equals(accessToken)) {
            // 将 access_token 放到 request 中 , 拦截通过
            request.setAttribute(ACCESS_TOKEN_HEADER, accessToken);
            return true;
        } else {
            // 查找 redis 内是否有 refresh_token
            String refreshTokenFromRedis = String.valueOf(redisUtil.get(username + TokenGenerator.TOKEN_SUFFIX_REFRESH));

            // 如果 redis 内没有相应的 refresh_token ，则过期，需要重新登录
            if(StringUtil.isEmpty(refreshTokenFromRedis)) {
                throw new BaseException(ResEnum.TOKEN_INVALID);
            } else {
                throw new BaseException(ResEnum.TOKEN_EXPIRED);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
