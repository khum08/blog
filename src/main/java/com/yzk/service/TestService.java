package com.yzk.service;

import com.yzk.mapper.TestMapper;
import com.yzk.model.response.Test;
<<<<<<< HEAD

=======
>>>>>>> 7becfc58bc36f94548444a70c1aeb87dbeb3e5f0
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public Test test(){
        return testMapper.selectTest();
    }

}
