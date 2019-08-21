package com.galdon.rrp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test1")
    public String test1() {
        return "ok";
    }


}
