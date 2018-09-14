package com.yzk.model;

import java.io.Serializable;

/**
 * 所有请求响应的封装类
 *
 * @author khum
 */
public class Response implements Serializable{

    private Integer status;
    private Object data;
    private String message;

    private Response(){}

    private Response(Builder builder){
        this.status = builder.status;
        this.data = builder.data;
        this.message = builder.message;
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
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", data=" + data +
                ", message=" + message +
                '}';
    }

    /**
     * 使用建造者模式构建响应体
     * @author khum
     *
     */
    public static class Builder{

        private Integer status;
        private Object data;
        private String message;

        public Builder(){}

        public Builder setStatus(Integer status){
            this.status = status;
            return this;
        }

        public Builder setData(Object data){
            this.data = data;
            return this;
        }

        public Builder setMessage(String message){
            this.message = message;
            return this;
        }

        public Response build(){
            return new Response(this);
        }

    }



}
