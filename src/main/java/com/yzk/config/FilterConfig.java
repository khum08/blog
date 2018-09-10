package com.yzk.config;

import com.yzk.filter.ExceptionHandlerFilter;
import com.yzk.filter.JwtFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        registrationBean.setOrder(1);
        registrationBean.setName("ExceptionHandlerFilter");
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
        registrationBean.setOrder(2);
        registrationBean.setName("JwtFilter");
        registrationBean.addUrlPatterns(urlPatterns.toArray(new String[urlPatterns.size()]));
        return registrationBean;
    }


}
