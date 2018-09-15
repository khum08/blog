package com.yzk.config;

import com.yzk.filter.ExceptionHandlerFilter;
import com.yzk.filter.JwtFilter;
import com.yzk.filter.SignFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/10
 *     desc   : 拦截器装配
 * </pre>
 */
@Configuration
public class FilterConfig {


    /**
     * 捕捉拦截器中抛出的异常
     * @return
     */
    @Bean
    public FilterRegistrationBean exceptionHandlerFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ExceptionHandlerFilter());
        registrationBean.setOrder(10);
        registrationBean.setName("ExceptionHandlerFilter");
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    /**
     * 验证签名
     * Profile 控制此拦截器只在pro生产环境下生效
     * @return
     */
    @Profile("pro")
    @Bean
    public FilterRegistrationBean signFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SignFilter());
        registrationBean.setOrder(11);
        registrationBean.setName("signFilter");
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    /**
     * jwt拦截器
     * @return
     */
    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/apis/article/detail");
        urlPatterns.add("/user/authorize");
        urlPatterns.add("/user/refresh_token");
        registrationBean.setOrder(12);
        registrationBean.setName("JwtFilter");
        registrationBean.addUrlPatterns(urlPatterns.toArray(new String[urlPatterns.size()]));
        return registrationBean;
    }



}
