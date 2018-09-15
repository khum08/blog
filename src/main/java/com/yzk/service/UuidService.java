package com.yzk.service;

import com.yzk.mapper.UuidMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 *     author : khum 1241367422@qq.com
 *     time   : 2018/9/13
 *     desc   :
 * </pre>
 */
@Service
public class UuidService {

    @Autowired
    UuidMapper mMapper;

    public void create(String uuid){
        mMapper.insertUuid(uuid);
    }

    @Transactional
    public String selectNext(){
        int nowNum = mMapper.nowNum();
        mMapper.updateNum();
        return mMapper.nextUuid(nowNum);
    }


}
