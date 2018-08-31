package com.yzk.service;

import com.yzk.exception.ExceptionEnum;
import com.yzk.mapper.ArticleMapper;
import com.yzk.model.Response;
import com.yzk.model.domain.Article;
import com.yzk.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/31
 *     desc   :
 * </pre>
 */
@Service
public class ArticleService {

    @Autowired
    ArticleMapper mMapper;

    /**
     * 发布文章
     * @param article
     * @return
     */
    @Transactional
    public Response push(Article article) {
        int i = mMapper.push_article(article);
        if(i>0){
            int id = mMapper.article_max_id();
            article.setId(id);
            int j = mMapper.push_article_c(article);
            if (j>0){
                return ResponseUtil.success();
            }else{
                return ResponseUtil.error(ExceptionEnum.HTTP_DB_ERROR);
            }
        }else{
            return ResponseUtil.error(ExceptionEnum.HTTP_DB_ERROR);
        }
    }

    /**
     * 改变文章的状态
     * 0 ： 默认
     * 1 ： 发布
     * 2 ： 下架
     * @param id
     * @param status
     * @return
     */
    public Response changeStatus(int id, int status){
        Article article = mMapper.selectArticleById(id);
        if(article== null){
            return ResponseUtil.error(51010,"该文章不存在");
        }else{
            int result = mMapper.changeStatus(id, status);
            if( result>0 ){
                return ResponseUtil.success();
            }else{
                return ResponseUtil.error(ExceptionEnum.HTTP_DB_ERROR);
            }
        }
    }


    /**
     * 根据目录id 查出文章概述
     * @param category
     * @return
     */
    public Response getArticlesByCategory(int category) {
        List<Article> list = mMapper.getArticlesByCategory(category);
        return ResponseUtil.success(list);
    }

    /**
     * 更新文章内容
     * @param article
     * @return
     */
    @Transactional
    public Response modify(Article article){
        Article exit = mMapper.selectArticleById(article.getId());
        if(exit == null){
            return ResponseUtil.error(51011,"该文章不存在");
        }
        mMapper.modify(article);
        mMapper.modify_article_c(article);
        return ResponseUtil.success();
    }

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    @Transactional
    public Response lookDetail(int id){
        Article article = mMapper.selectArticleById(id);
        if(article==null){
            return ResponseUtil.error(51011,"该文章不存在");
        }
        String content = mMapper.selectContentById(id);
        article.setContent(content);
        mMapper.visited(id);
        return ResponseUtil.success(article);
    }
}
