package com.yzk.mapper;

import com.yzk.model.Response.Test;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMapper {

    @Select("select * from test")
    Test selectTest();
}
