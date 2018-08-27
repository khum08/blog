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
     * @param data
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
     * @param code
     * @param message
     * @return
     */
    public static Response error(int status,String message){
        return new Response.Builder()
                .setStatus(status)
                .setErrorMessage(message)
                .build();
    }





}
