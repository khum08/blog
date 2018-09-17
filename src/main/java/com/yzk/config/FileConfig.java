package com.yzk.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * <pre>
 *     author : khum 1241367422@qq.com
 *     time   : 2018/9/17
 *     desc   : 文件上传限制
 * </pre>
 */
@Configuration
public class FileConfig {

    /**
     * 设置上传文件大小，配置文件属性设置无效
     * spring.http.multipart.maxFileSize=100Mb
     * spring.http.multipart.maxRequestSize=100Mb
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigBean(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("100MB");
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }
}
