package com.yzk.model;

/**
 * 所有请求响应的封装类
 *
 * @author khum
 * @param <T>
 */
public class Response {

    private Integer status;
    private Object data;
    private String errorMessage;

    private Response(){}

    private Response(Builder builder){
        this.status = builder.status;
        this.data = builder.data;
        this.errorMessage = builder.errorMessage;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "JsonResult { status =" + status + ", data=" + data + ", error=" + errorMessage + "}";
    }

    /**
     * 使用建造者模式构建响应体
     * @author khum
     *
     */
    public static class Builder{

        private Integer status;
        private Object data;
        private String errorMessage;

        public Builder(){}

        public Builder setStatus(Integer status){
            this.status = status;
            return this;
        }

        public Builder setData(Object data){
            this.data = data;
            return this;
        }

        public Builder setErrorMessage(String message){
            this.errorMessage = message;
            return this;
        }

        public Response build(){
            return new Response(this);
        }

    }



}
