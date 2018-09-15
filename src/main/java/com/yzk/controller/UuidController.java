package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.service.UuidService;
import com.yzk.util.ResponseUtil;
import com.yzk.util.UUIDUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *     author : khum 1241367422@qq.com
 *     time   : 2018/9/13
 *     desc   : 生成唯一id
 * </pre>
 */
@RestController
@RequestMapping("/util")
public class UuidController {

    @Autowired
    UuidService mUuidService;

    /**
     * 生成UUID
     * @param quantity
     * @return
     */
    @GetMapping("/uuid/{quantity}")
    public Response createUuid(@PathVariable("quantity") int quantity){
        for(int i = 0; i < quantity; i++){
            String uuid = UUIDUtil.get();
            mUuidService.create(uuid);
        }
        return ResponseUtil.success();
    }

    /**
     * 返回下一条UUID
     * @return
     */
    @GetMapping("/uuid")
    public Response getNext(){
        return ResponseUtil.success(mUuidService.selectNext());
    }

}
