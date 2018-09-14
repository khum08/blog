package com.yzk.exception;

import com.yzk.model.Response;
import com.yzk.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.xml.bind.ValidationException;

/**
 * <pre>
 *     author : khum 1241367422@qq.com
 *     time   : 2018/9/11
 *     desc   : 全局异常捕捉类，注意此类不能捕捉Filter中的异常
 * </pre>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 系统未知错误
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response handler(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return ResponseUtil.error(ExceptionEnum.HTTP_UNKNOWN);
    }

    /**
     * 自定义的访问错误
     * @param exception
     * @return
     */
    @ExceptionHandler(AccessException.class)
    public Response accessExceptionHandler(AccessException exception) {
        return ResponseUtil.error(exception.getStatus(), exception.getMessage());
    }

    /**
     * 缺少参数异常
     * @param exception
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response missingParameterHandler(MissingServletRequestParameterException exception){
        return ResponseUtil.error(49000, exception.getMessage());
    }

    /**
     * 请求方式不支持
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response methodNotSupportedHandler(HttpRequestMethodNotSupportedException exception){
        return ResponseUtil.error(49001, exception.getMessage());
    }

    /**
     * 没有此方法
     * @param exception
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Response methodNotSupportedHandler(NoHandlerFoundException exception){
        return ResponseUtil.error(49002, exception.getMessage());
    }

    /**
     * 参数验证失败
     * @param exception
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class})
    public Response methodNotSupportedHandler (Exception exception){
        return ResponseUtil.error(49003, exception.getMessage());
    }

    /**
     * 参数绑定失败
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Response methodNotSupportedHandler (BindException exception){
        return ResponseUtil.error(49004, exception.getMessage());
    }

    /**
     * JSON_parse_error
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response httpMessageNotReadableHandler (HttpMessageNotReadableException exception){
        return ResponseUtil.error(49006, exception.getMessage());
    }

}
