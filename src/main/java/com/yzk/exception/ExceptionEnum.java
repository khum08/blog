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
    HTTP_UNKNOWN(-1,"未知错误"),
    HTTP_TYPE_ERROR(49005,"参数类型不匹配"),
    HTTP_EMPTY_PARAM(40000,"缺少参数"),
    HTTP_DB_ERROR(50000,"数据库操作失败"),
    HTTP_LOGIN_FIRST(40000,"请先登录"),
    HTTP_LOGIN_ERROR(40001,"登录异常"),
    HTTP_TOKEN_EXPIRED(49100, "token失效"),
    HTTP_TOKEN_SIGNATURE(49101, "token签名错误"),
    HTTP_TOKEN_ARGS(49102, "token参数错误"),
    HTTP_TOKEN_ERROR(49103, "token解析错误"),
    HTTP_TOKEN_MALFORMED(49104, "token格式错误"),
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
        for (ExceptionEnum exception :ExceptionEnum.values()) {
            if (exception.getStatus() == key) {
                return exception;
            }
        }
        return null;
    }

}