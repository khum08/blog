package com.yzk.mapper;

import com.yzk.model.domain.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    @Insert("insert into user(username, email, password, auth, is_enabled) " +
            "values(#{username}, #{email}, #{password}, #{auth}, #{is_enabled})")
    int register(User user);

    @Select("select *from user where id= #{userid}")
    User findUserById(Integer userid);

    @Update("update user set auth=#{auth} where id=#{id}")
    int authorize(@Param("id") Integer id, @Param("auth") String auth);
}
