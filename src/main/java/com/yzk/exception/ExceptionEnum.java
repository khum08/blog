package com.yzk.exception;

/**
 * 统一管理自定义的错误码
 * 调用者应该还需自己封装统一的http状态码
 *
 * 思考：因为枚举的占用空间更大(是类的近2倍),考虑是否自定义Pair来代替枚举，
 * 新思路：注解IntOf，StringOf
 * @author khum
 *
 */
public enum ExceptionEnum {

    HTTP_OK(0,"请求成功"),
    HTTP_UNKNOW(-1,"未知错误"),
    HTTP_TYPE_ERROR(-2,"参数类型不匹配"),
    HTTP_EMPTY_PARAM(40000,"缺少参数"),
    HTTP_DB_ERROR(50000,"数据库操作失败"),
    ;
    ExceptionEnum(Integer status,String message){
        this.status = status;
        this.message = message;
    }

    private Integer status;
    private String message;
    public Integer getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }

    public static ExceptionEnum getEnumByKey(int key){
        for (ExceptionEnum exception :
                ExceptionEnum.values()) {
            if (exception.getStatus() == key) {
                return exception;
            }
        }
        return null;
    }

}