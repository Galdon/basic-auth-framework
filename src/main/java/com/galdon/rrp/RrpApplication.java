package com.galdon.rrp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: rrp
 * @description: RRP 启动类
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = "com.galdon.rrp.dao")
public class RrpApplication {

    public static void main(String[] args) {
        SpringApplication.run(RrpApplication.class, args);
    }

}
