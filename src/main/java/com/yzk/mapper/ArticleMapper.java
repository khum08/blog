package com.yzk.mapper;

import com.yzk.model.domain.Article;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/31
 *     desc   :
 * </pre>
 */
@Repository
public interface ArticleMapper {

    @Insert("insert into article(category, create_at, update_time, visited, title, summary) " +
            "values(#{category},#{create_at}, #{update_time}, #{visited}, #{title}, #{summary}) ")
    int push_article(Article article);

    @Insert("insert into article_c(a_id, content) values (#{id}, #{content})")
    int push_article_c(Article article);

    @Select("select max(id) from article")
    int article_max_id();

    @Select("select id,category,status,create_at,update_time,visited,title,summary from article where id = #{id}")
    Article selectArticleById(@Param("id") int id);

    @Select("select content from article_c where a_id=#{id}")
    String selectContentById(@Param("id") int id);

    @Update("update article set status=#{status} where id=#{id}")
    int changeStatus(@Param("id") int id, @Param("status") int status);

    @Select("select id,category,status,create_at,update_time,visited,title,summary from article where category = #{category}")
    List<Article> getArticlesByCategory(@Param("category") int category);

    @Update("update article set category=#{category}, status=#{status}," +
            "update_time=#{create_at},visited=#{create_at},title=#{title},summary=#{summary} where id = #{id}")
    int modify(Article article);

    @Update("update article_c set content=#{content} where a_id = #{id}")
    int modify_article_c(Article article);

    @Update("update article set visited=visited+1 where id=#{id}")
    int visited(@Param("id") int id);


}
