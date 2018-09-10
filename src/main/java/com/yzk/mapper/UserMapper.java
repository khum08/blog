package com.yzk.mapper;

import com.yzk.model.domain.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
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

    @Select("select *from user where username = #{username};")
    User findUserByUsername(User user);

    @Insert("insert into user(username, email, password) values(#{username}, #{email}, #{password})")
    int register(User user);
}
