package com.yzk.service;

import com.yzk.mapper.TestMapper;
import com.yzk.model.Response.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    TestMapper testMapper;


    public Test test(){
        return testMapper.selectTest();
    }

}
