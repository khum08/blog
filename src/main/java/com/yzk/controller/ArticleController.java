package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.model.domain.Article;
import com.yzk.service.ArticleService;
import com.yzk.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/31
 *     desc   : 文章相关的接口
 * </pre>
 */
@RestController
@RequestMapping("/apis/article")
public class ArticleController extends BaseController{

    @Autowired
    ArticleService mService;

    /**
     * json提交
     * @param article
     * @return
     */
    @PostMapping("/push")
    public Response push_article(@RequestBody Article article) {
        int currentTime = (int) (new Date().getTime() / 1000);
        article.setStatus(0);
        article.setVisited(0);
        article.setCreate_at(currentTime);
        article.setUpdate_time(currentTime);
        return mService.push(article);
    }

    @GetMapping("/change_status")
    public Response release(@RequestParam("id") int id, @RequestParam("status") int status) {
        if (status < 0 || status > 3) {
            return ResponseUtil.error(40010, "该状态不存在");
        }
        return mService.changeStatus(id, status);
    }

    @PostMapping("/modify")
    public Response modify(@RequestBody Article article) {
        article.setStatus(0);
        article.setUpdate_time((int)(new Date().getTime()/1000));
        return mService.modify(article);
    }

    @GetMapping
    public Response getArticlesByCategory(@RequestParam("category") int category) {
        return mService.getArticlesByCategory(category);
    }

    @GetMapping("/detail")
    public Response getArticleById(@RequestParam("a_id") int id){
        return mService.lookDetail(id);
    }

}
