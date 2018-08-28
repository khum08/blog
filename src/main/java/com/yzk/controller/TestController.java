package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.service.TestService;

import com.yzk.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/")
    public String test(){
        return "hello world";
    }

    @GetMapping("/test/")
    public Response test2(){
        return ResponseUtil.success(testService.test());
    }

    @GetMapping("/account")
    public String test3(){
        return "hello world";
    }


}
