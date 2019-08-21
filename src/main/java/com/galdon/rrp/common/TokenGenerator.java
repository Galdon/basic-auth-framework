package com.galdon.rrp.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @program: rrp
 * @description: 自定义 Token 生成器
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
public class TokenGenerator {

    private static String ACCESS_KEY = "GAOZHI_ACCESS";
    private static String REFRESH_KEY = "GAOZHI_REFRESH";
    private static String ISSUER = "gaozhi";

    public static long ACCESS_EXPIRE = 86400;
    public static long REFRESH_EXPIRE = 2592000;
    public static String CLAIMS_USERNAME = "name";

    public static final String TOKEN_TYPE_ACCESS = "ACCESS_TOKEN";
    public static final String TOKEN_TYPE_REFRESH = "REFRESH_TOKEN";

    public static String TOKEN_SUFFIX_ACCESS = ":access_token";
    public static String TOKEN_SUFFIX_REFRESH = ":refresh_token";

    /**
     * 根据用户名生成 token
     * @param username
     * @return
     */
    public static String generateToken(String username, String tokenType) {
        String key = tokenType.equals(TOKEN_TYPE_ACCESS) ? ACCESS_KEY : REFRESH_KEY;
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")    //设置header
                .setHeaderParam("alg", "HS256")
                .setIssuedAt(new Date())     //设置iat
                .claim(CLAIMS_USERNAME, username)   //设置payload的键值对
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS256, key);    //签名，需要算法和key
        return builder.compact();
    }

    /**
     * 获取 token 内的载荷
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token, String tokenType) {
        String key = tokenType.equals(TOKEN_TYPE_ACCESS) ? ACCESS_KEY : REFRESH_KEY;
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (Exception e) {
            BaseException be = new BaseException(ResEnum.TOKEN_INVALID);
            throw be;
        }
    }
}
