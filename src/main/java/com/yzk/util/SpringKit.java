package com.yzk.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *     author : yuanzhenkun 1241367422@qq.com
 *     time   : 2018/9/12
 *     desc   : spring ioc工具类
 * </pre>
 */
@Component
public class SpringKit implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringKit.applicationContext == null) {
            SpringKit.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }


    //根据name
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    //根据类型
    public static <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }

    public static <T> T getBean(String name,Class<T> tClass){
        return applicationContext.getBean(name, tClass);
    }

}
