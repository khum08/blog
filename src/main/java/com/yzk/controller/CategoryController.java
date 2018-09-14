package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.service.CategoryService;
import com.yzk.util.RequestUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/29
 *     desc   : 目录相关的接口
 * </pre>
 */
@RestController
@RequestMapping("/apis/categories")
public class CategoryController extends BaseController {

    @Autowired
    CategoryService mService;

    /**
     * 表单提交
     * RequestParam和参数名相同时，value可以不写
     * @param name
     * @param parent_id
     * @return
     */
    @PostMapping("/add")
    public Response add(@RequestParam(value = "name", required = true) String name,
                        @RequestParam(value = "parent_id", required = true) int parent_id){
        RequestUtil.isEmpty(name);
        return mService.addChild(name, parent_id);
    }

    /**
     * delete请求
     * @param node_id
     * @return
     */
    @DeleteMapping("/{node_id}")
    public Response remove(@PathVariable("node_id") int node_id){
        return mService.removeNode(node_id);
    }

    @GetMapping("/")
    public Response showTree(){
        return mService.showTree();
    }

    /**
     * www-form-urlencoded传递参数parent_id
     * @param node_id
     * @param parent_id
     * @return
     */
    @PutMapping("/{node_id}")
    public Response changeParent(@PathVariable("node_id") int node_id,
                                 @RequestParam("parent_id") int parent_id){
        return mService.changeParent(node_id, parent_id);
    }


}
