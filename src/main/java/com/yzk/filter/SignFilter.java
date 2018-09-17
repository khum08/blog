package com.yzk.filter;

import com.yzk.exception.AccessException;
import com.yzk.util.SpringKit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.GenericFilterBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *     author : khum 1241367422@qq.com
 *     time   : 2018/9/11
 *     desc   : 校验请求的签名
 * </pre>
 */
public class SignFilter extends GenericFilterBean{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Qualifier("custom")
    @Autowired(required = false)
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        logger.info("pass SignFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //这个字段为了让服务器通过app_id查找到对应的app_secret
        String app_id = request.getParameter("app_id");
        //这个预留字段可以过滤例如1分钟不能请求两次的场景，可以这次请求放入Redis
        String time = request.getParameter("time");
        //这个字段用处理一次点击两次请求的情况
        String rand_str = request.getParameter("rand_str");
        //filter中自动装配bean会失败，手动处理
        if (redisTemplate == null){
            redisTemplate = (RedisTemplate)SpringKit.getBean("custom");
        }
        String app_secret = null;
        try{
            app_secret = redisTemplate.opsForValue().get(app_id)+"";
        }catch (Exception e){
            throw new AccessException(49141,"客户端非法");
        }
        String clientSign = request.getParameter("sign");
        String requestBody = getRequestBody(request);
        if (clientSign == null ||  clientSign.isEmpty() ||
                app_id == null ||app_id.isEmpty() ||
                time == null || time.isEmpty() ||
                rand_str == null || rand_str.isEmpty()){
            throw new AccessException(49120, "缺少请求签名参数");
        }
        if ( requestBody== null || requestBody.isEmpty()) {
            //没有请求体的不拦截
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            //验证签名
            String serverSign = computeSign(requestBody, time, rand_str, app_secret);
            if (serverSign!=null && serverSign.equals(clientSign)){
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                throw new AccessException(49121, "请求签名SIGN错误");
            }
        }
    }

    /**
     * 获取请求体JSON
     * @param request
     * @return
     */
    private String getRequestBody(HttpServletRequest request){
        String requestBody = null;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;
            while ((line = bufferedReader.readLine())!=null){
                sb.append(line);
            }
            requestBody = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestBody;
    }

    /**
     * 测试签名算法
     * @param arg
     */
    public static void main(String[] arg){
        String jsonContent = "{\"name\":\"xiaoming\",\"ta\":\"ta\"}";
        SignFilter signFilter = new SignFilter();
        String s = signFilter.computeSign(jsonContent, "1234565432", "random", "secret");
        System.out.println(s);
    }

    /**
     * 计算签名
     * @param jsonContent
     * @param appSecret
     * @return
     */
    private String computeSign(String jsonContent, String time, String rand_str, String appSecret) {
        try {
            JSONObject jsonObject = new JSONObject(jsonContent);
            jsonObject.put("time", time);
            jsonObject.put("rand_str", rand_str);
            Iterator<String> keys = jsonObject.keys();
            List list = new ArrayList();
            while (keys.hasNext()) {
                list.add(keys.next());
            }
            Collections.sort(list);
            StringBuilder stringBuilder = new StringBuilder();
            for (Object obj : list) {
                String key = obj.toString();
                stringBuilder.append(key + jsonObject.get(key));
            }
            String paramStr = stringBuilder.toString() + appSecret;
            System.out.println("rawSign: "+paramStr);
            return getSha1(paramStr).toUpperCase();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA1加密
     * @param str
     * @return
     */
    private String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
