package com.yzk.service;

import com.yzk.exception.AccessException;
import com.yzk.exception.ExceptionEnum;
import com.yzk.mapper.CategoryMapper;
import com.yzk.model.domain.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public int addChild(String name, int parent_id) {
        Category category = null;
        if(parent_id > 0){
            category = mMapper.selectOne(parent_id);
        }
        if(category == null ){
            throw new AccessException(ExceptionEnum.HTTP_PARENT_NOT_EXIT);
        }
        mMapper.addChild(name, parent_id);
        return 0;
    }
}
