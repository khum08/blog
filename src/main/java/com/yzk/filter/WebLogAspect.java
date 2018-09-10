package com.yzk.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/10
 *     desc   : 利用AOP打印请求响应
 * </pre>
 */
@Component
@Aspect
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.yzk.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("--------------------------- Request ----------------------------------");
        logger.info("request_url : " + request.getRequestURL().toString());
        logger.info("http_method : " + request.getMethod());
        logger.info("ip : " + request.getRemoteAddr());
        logger.info("class_method : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("args : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("--------------------------- Request END----------------------------------");

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("--------------------------- Response ----------------------------------");
        logger.info("Response: " + ret);
        logger.info("--------------------------- Response END----------------------------------");
    }

}
