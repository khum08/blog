package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * <pre>
 *     author : khum 1241367422@qq.com
 *     time   : 2018/9/14
 *     desc   :
 * </pre>
 */
public abstract class BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 校验
     * @param bindingResult
     * @return 返回第0条校验信息
     */
    protected Response valid(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return ResponseUtil.error(49003, allErrors.get(0).getDefaultMessage());
        }else{
            return null;
        }
    }
}
