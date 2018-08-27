package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("categories")
public class CategoryController {

    @PostMapping("/add")
    public Response add(String name, String parent){
        return ResponseUtil.success(name +" and "+ parent);
    }

    @GetMapping("/remove")
    public Response remove(@RequestParam String name){
        return ResponseUtil.success(name);
    }




}
