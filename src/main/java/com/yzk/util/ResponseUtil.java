package com.yzk.util;

import com.yzk.exception.AccessException;
import com.yzk.exception.ExceptionEnum;
import com.yzk.model.Response;

/**
 * 快速格式化输出类
 * @author khum
 *
 */
public class ResponseUtil {

    /**
     * 请求成功
     * @param data
     * @return
     */
    public static Response success (Object data){
        return new Response.Builder()
                .setStatus(ExceptionEnum.HTTP_OK.getStatus())
                .setData(data)
                .setErrorMessage(ExceptionEnum.HTTP_OK.getMessage())
                .build();
    }

    /**
     * 请求成功
     * @return
     */
    public static Response success (){
        return new Response.Builder()
                .setStatus(ExceptionEnum.HTTP_OK.getStatus())
                .setErrorMessage(ExceptionEnum.HTTP_OK.getMessage())
                .build();
    }

    /**
     * 请求失败
     * @param exception
     * @return
     */
    public static Response error(ExceptionEnum exception){
        return new Response.Builder()
                .setStatus(exception.getStatus())
                .setErrorMessage(exception.getMessage())
                .build();
    }

    /**
     * 请求失败
     * @param excepton
     * @return
     */
    public static Response error(AccessException excepton){
        return new Response.Builder()
                .setStatus(excepton.getStatus())
                .setErrorMessage(excepton.getMessage())
                .build();
    }

    /**
     * 请求失败
     *
     * @param status
     * @param message
     * @return
     */
    public static Response error(int status,String message){
        return new Response.Builder()
                .setStatus(status)
                .setErrorMessage(message)
                .build();
    }

    /**
     * 根据不同的状态码返回不同的Response
     * 此方法只是利于快速写代码，不建议调用此方法
     * @param status
     * @return
     */
    public static Response reply(int status){
        ExceptionEnum exceptionEnum = ExceptionEnum.getEnumByKey(status);
        if (exceptionEnum == null){
            throw new RuntimeException("此状态码没有在ExceptionEnum中定义");
        }
        return error(exceptionEnum);
    }


}
