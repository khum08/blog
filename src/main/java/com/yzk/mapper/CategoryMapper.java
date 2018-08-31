package com.yzk.mapper;

import com.yzk.model.domain.Category;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/30
 *     desc   :
 * </pre>
 */
@Repository
public interface CategoryMapper {


    @Insert("insert into categories(name, parent_id) values(#{name}, #{parent_id})")
    int addChild(@Param("name") String name, @Param("parent_id") int parent_id);

    @Select("select node_id, name, parent_id from categories where node_id = #{node_id}")
    Category selectOne(@Param("node_id") int node_id);

    @Select("select node_id, name, parent_id from categories where parent_id = #{node_id}")
    List<Category> selectChild(@Param("node_id") int parent_id);

    @Delete("delete from categories where node_id = #{node_id}")
    int removeNode(@Param("node_id") int node_id);

    @Select("select node_id, name, parent_id from categories")
    List<Category> selectAll();

    @Update("update categories set parent_id=#{parent_id} where node_id = #{node_id}")
    int changeParent(@Param("node_id") int node_id, @Param("parent_id") int parent_id);


}
