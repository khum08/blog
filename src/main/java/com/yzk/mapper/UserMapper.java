package com.yzk.mapper;

import com.yzk.model.domain.User;

import org.springframework.stereotype.Repository;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   :
 * </pre>
 */
@Repository
public interface UserMapper {

    User login(User user);

}
