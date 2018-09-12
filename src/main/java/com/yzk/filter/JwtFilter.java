package com.yzk.filter;

import com.yzk.exception.AccessException;
import com.yzk.model.domain.Audience;
import com.yzk.util.JwtHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static com.yzk.exception.ExceptionEnum.HTTP_LOGIN_ERROR;
import static com.yzk.exception.ExceptionEnum.HTTP_LOGIN_FIRST;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   : 校验token
 * </pre>
 */
public class JwtFilter extends GenericFilterBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Audience mAudience;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        logger.debug("----------------this is JwtFilter---------------------------");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String authHeader = req.getHeader("authorization");
        if ("OPTIONS".equals(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (authHeader == null || !authHeader.startsWith("bearer;")) {
                throw new AccessException(HTTP_LOGIN_FIRST);
            }
            String authorization = authHeader.substring(7);
            //filter中注入会失败，因为bean还没有初始化
            if (mAudience == null) {
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
                mAudience = (Audience) factory.getBean("audience");
            }
            Claims claims = JwtHelper.parseJwt(authorization, mAudience.getBase64Secret());
            if (claims == null) {
                throw new AccessException(HTTP_LOGIN_ERROR);
            }
            //把解析token得到的个人信息放在request中
            req.setAttribute("claims", claims);
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
