package com.yzk.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *     author : khum 1241367422@qq.com
 *     time   : 2018/9/13
 *     desc   :
 * </pre>
 */
@Repository
public interface UuidMapper {

    @Insert("insert into uuid (value) values(#{uuid});")
    void insertUuid(@Param("uuid") String uuid);

    @Update("update uuid_num set num=num+1;")
    void updateNum();

    @Select("select num from uuid_num limit 1;")
    int nowNum();

    @Select("select value from uuid limit #{num},1;")
    String nextUuid(@Param("num") int num);

}
