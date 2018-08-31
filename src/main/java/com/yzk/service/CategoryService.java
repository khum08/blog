package com.yzk.service;

import com.yzk.mapper.CategoryMapper;
import com.yzk.model.Response;
import com.yzk.model.domain.Category;
import com.yzk.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/30
 *     desc   :
 * </pre>
 */
@Service
public class CategoryService {

    @Autowired
    CategoryMapper mMapper;

    /**
     * 添加一个目录，需校验父目录是否存在，
     * 当父目录id为0时，说明它是根目录
     * @param name
     * @param parent_id
     * @return
     */
    public Response addChild(String name, int parent_id) {
        if (parent_id == 0){//没有父节点
            List<Category> categories = mMapper.selectChild(parent_id);
            if (!categories.isEmpty()){
                return ResponseUtil.error(51003, "根目录已经存在");
            }
            return _addChild(name, parent_id);
        }else{//有父节点
            Category category = mMapper.selectOne(parent_id);
            if(category == null){
                return ResponseUtil.error(51000, "父节点不存在");
            }else{
                return _addChild(name, parent_id);
            }
        }

    }

    private Response _addChild(String name, int parent_id){
        int result = mMapper.addChild(name, parent_id);
        if (result == 0 ){
            return ResponseUtil.error(51002,"数据库操作失败");
        }else{
            return ResponseUtil.success();
        }
    }

    /**
     * 删除一个目录，应当删除他的子目录
     * @param node_id
     * @return
     */
    public Response removeNode(int node_id){
        int result = mMapper.removeNode(node_id);
        if (result == 0) {
            return ResponseUtil.error(51004,"该节点不存在");
        }else{
            _recursiveChild(node_id);
            return ResponseUtil.success();
        }
    }

    /**
     * 递归删除子目录
     * @param node_id
     */
    private void _recursiveChild(int node_id){
        List<Category> categories = mMapper.selectChild(node_id);
        if(!categories.isEmpty()){
            for(Category category: categories){
                mMapper.removeNode(category.getNode_id());
                _recursiveChild(category.getNode_id());
            }
        }
    }


    /**
     * 展示这颗树
     */
    public Response showTree(){
        List<Category> categories = mMapper.selectAll();
        return ResponseUtil.success(categories);
    }


    /**
     * 移动一个目录
     * @param node_id
     * @param parent_id
     */
    public Response changeParent(int node_id, int parent_id) {
        Category category = mMapper.selectOne(node_id);
        if (category==null){
            return ResponseUtil.error(51004, "该节点不存在");
        }
        category = mMapper.selectOne(node_id);
        if (category == null){
            return ResponseUtil.error(51000, "父节点不存在");
        }
        int result = mMapper.changeParent(node_id, parent_id);
        if(result==0 ){
            return ResponseUtil.error(51002,"数据库操作失败");
        }else{
            return ResponseUtil.success();
        }

    }
}
