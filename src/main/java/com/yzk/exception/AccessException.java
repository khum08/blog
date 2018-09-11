package com.yzk.exception;

/**
 * 统一抛出的异常
 * 抛出来的异常必须在ExceptionEnum中，便于以后统一管理
 * @author khum
 *
 */
public class AccessException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AccessException(ExceptionEnum exception){
        super(exception.getMessage());
        this.status = exception.getStatus();
    }

    public AccessException(int status, String message){
        super(message);
        this.status = status;
    }
    /**
     * 此处是为了构成异常的链化，这样能准确定位多个微服务中具体发生异常的地方
     * 不过此方法是否可行需进一步考虑
     * @param exception
     * @param t
     */
    public AccessException(ExceptionEnum exception,Throwable t){
        super(exception.getMessage(),t);
        this.status = exception.getStatus();
    }

    private Integer status;
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

}
