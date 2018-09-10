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

    public User findUserByUsername(User user){
        User queryUer = mMapper.findUserByUsername(user);
        return queryUer;
    }

    public int register(User user) {
        User queryUer = findUserByUsername(user);
        if (queryUer != null) {
            return -1;
        }
        int register = mMapper.register(user);
        return register;
    }
}
