package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.service.CategoryService;
import com.yzk.util.RequestUtil;
import com.yzk.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/apis/categories")
public class CategoryController {

    @Autowired
    CategoryService mService;

    @PostMapping("/add")
    public Response addChild(String name, int parent_id){
        RequestUtil.isEmpty(name);
        mService.addChild(name, parent_id);
        return ResponseUtil.success();
    }

    @GetMapping("/remove")
    public Response remove(@RequestParam String name){
        return ResponseUtil.success(name);
    }




}
