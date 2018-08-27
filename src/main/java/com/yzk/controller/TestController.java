package com.yzk.controller;

import com.yzk.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public String test(){
        return "hello world";
    }

    @GetMapping("/test/")
    public String test2(){
        return testService.test().toString();
    }


}
