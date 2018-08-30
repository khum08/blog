package com.yzk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/30
 *     desc   : 模板视图
 * </pre>
 */
@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("login")
    public String login(){
        return "login";
    }
}
