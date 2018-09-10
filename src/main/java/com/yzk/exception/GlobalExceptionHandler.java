package com.yzk.exception;

import com.yzk.model.Response;
import com.yzk.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 捕捉其他代码中抛出的异常
 * @author khum
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义的访问错误
     * @param exception
     * @return
     */
    @ExceptionHandler(AccessException.class)
    @ResponseBody
    public Response accessExceptionHandler(AccessException exception) {
        return ResponseUtil.error(exception.getStatus(), exception.getMessage());
    }

    /**
     * 系统未知错误
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handler(Exception exception) {
        logger.error("系统错误", exception);
        return ResponseUtil.error(ExceptionEnum.HTTP_UNKNOW);
    }

}
