package com.yzk.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("categories")
public class CategoryController {

    @PostMapping("/add")
    public void add(String name, String parent){
        System.out.print(name +" and "+ parent);
    }

    @GetMapping("/remove")
    public void remove(@RequestParam String name){
        System.out.print(name);
    }




}
