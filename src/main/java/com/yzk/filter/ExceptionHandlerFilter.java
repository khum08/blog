package com.yzk.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzk.exception.AccessException;
import com.yzk.model.Response;
import com.yzk.util.ResponseUtil;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/10
 *     desc   : GlobalExceptionHandler无法捕捉到filter中抛出的异常的，可以通过此方法。
 * </pre>
 */
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("pass ExceptionHandlerFilter");
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }catch (AccessException exception){
            Response response = ResponseUtil.error(exception);
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().write(convertObjectToJson(response));
        }
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
