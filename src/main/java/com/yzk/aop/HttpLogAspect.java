package com.yzk.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
 *     desc   : 利用AOP打印请求响应;
 *              计算请求执行消耗的时间;
 * </pre>
 */
@Component
@Aspect
public class HttpLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.yzk.controller..*.*(..))")
    public void httpLog(){}

    @Before("httpLog()")
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

    @AfterReturning(returning = "ret", pointcut = "httpLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("--------------------------- Response ----------------------------------");
        logger.info(""+ret);
        logger.info("--------------------------- Response END----------------------------------");
    }

    /**
     * 计算请求执行消耗的时间
     * @param pjp
     * @return
     */
    @Around("httpLog()")
    public Object around(ProceedingJoinPoint pjp){
        long startTime = System.currentTimeMillis();
        try {
            Object proceed = pjp.proceed();
            long endTime = System.currentTimeMillis();
            String methodName = _getMethodName(pjp);
            logger.info( "time used: " + methodName + "; "+ String.valueOf(endTime-startTime));
            return proceed;
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage());
            return null;
        }
    }

    /**
     * @param pjp
     * @return 返回调用的方法名
     */
    private String _getMethodName(ProceedingJoinPoint pjp){
        String name = pjp.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        String method = signature.getMethod().getName();
        return name + "." + method;
    }

}
