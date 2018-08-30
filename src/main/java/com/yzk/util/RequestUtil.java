package com.yzk.util;

import com.yzk.exception.AccessException;
import com.yzk.exception.ExceptionEnum;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/30
 *     desc   :
 * </pre>
 */
public class RequestUtil {

    /**
     * 检查参数是否为空
     * @param param
     */
    public static void isEmpty(String param){
        if("".equals(param) || param== null){
            throw new AccessException(ExceptionEnum.HTTP_EMPTY_PARAM);
        }
    }

    /**
     * 检查不固定参数是否为空
     * @param params
     */
    public static void isEmpty(String ...params){
        for (String param : params)
            isEmpty(param);
    }

}
