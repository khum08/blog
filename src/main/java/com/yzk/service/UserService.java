package com.yzk.service;

import com.yzk.mapper.UserMapper;
import com.yzk.model.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   :
 * </pre>
 */
@Service
public class UserService {

    @Autowired
    UserMapper mMapper;

    public User login(User user){
        User queryUer = mMapper.login(user);
        return queryUer;
    }
}
