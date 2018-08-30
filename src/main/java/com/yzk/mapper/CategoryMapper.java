package com.yzk.mapper;

import com.yzk.model.domain.Category;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/30
 *     desc   :
 * </pre>
 */
@Repository
public interface CategoryMapper {


    @Insert("insert into categories(name, parent_id) values(#{name},#{parent_id})")
    void addChild(String name, int parent_id);

    @Select("select name,node_id,parent_id from categories where node_id = #{node_id}")
    Category selectOne(int node_id);

}
