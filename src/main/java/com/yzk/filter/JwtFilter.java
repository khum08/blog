package com.yzk.filter;

import com.yzk.model.domain.Audience;
import com.yzk.util.JwtHelper;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   :
 * </pre>
 */
public class JwtFilter extends GenericFilterBean{

    @Autowired
    private Audience mAudience;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;
        String authHeader = req.getHeader("authorization");
        if("OPTIONS".equals(req.getMethod())){
            resp.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            if(authHeader==null|| !authHeader.startsWith("bearer;")){
                throw new RuntimeException("请先登录");
            }
            String authorization = authHeader.substring(7);
            if(mAudience==null){
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
                mAudience = (Audience)factory.getBean("audience");
            }
            Claims claims = JwtHelper.parseJwt(authorization, mAudience.getBase64Secret());
            if (claims == null) {
                throw new RuntimeException("登录异常");
            }
            req.setAttribute("claims", claims);
            filterChain.doFilter(servletRequest,servletResponse);
        }


    }
}
