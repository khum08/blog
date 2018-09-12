package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.service.RedisService;
import com.yzk.service.TestService;
import com.yzk.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    RedisService mRedisService;

    @GetMapping("/")
    public String test(){
        return "hello world";
    }

    @GetMapping("/test2/")
    public Response test2(){
        return ResponseUtil.success(testService.test());
    }

    @GetMapping("/account")
    public String test3(){
        return "hello world";
    }

    /**
     * 返回请求的响应体
     * @param request
     * @return
     */
    @PostMapping("/test3")
    public String test4(HttpServletRequest request){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向redis中存数据
     * @param key
     * @param value
     * @return
     */
    @PostMapping("/set")
    public String redisSet(@RequestParam String key,
                            @RequestParam String value){
        boolean success = mRedisService.set(key, value);
        return success?"success":"failure";
    }

    /**
     * 向Redis中取数据
     * @param key
     * @return
     */
    @GetMapping("/{key}")
    public String redisGet(@PathVariable("key") String key){
        Object o = mRedisService.get(key);
        return o.toString();
    }


}
