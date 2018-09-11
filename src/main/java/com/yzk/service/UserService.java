package com.yzk.service;

import com.yzk.exception.ExceptionEnum;
import com.yzk.mapper.UserMapper;
import com.yzk.model.Response;
import com.yzk.model.domain.Audience;
import com.yzk.model.domain.User;
import com.yzk.util.JwtHelper;
import com.yzk.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   :
 * </pre>
 */
@Service
public class UserService {

    @Autowired
    UserMapper mMapper;
    @Autowired
    Audience mAudience;

    public User findUserByUsername(User user){
        User queryUer = mMapper.findUserByUsername(user);
        return queryUer;
    }

    public int register(User user) {
        User queryUer = findUserByUsername(user);
        if (queryUer != null) {
            return -1;
        }
        int register = mMapper.register(user);
        return register;
    }

    /**
     * 给账号授权
     * @param userid
     * @param auth
     */
    public Response authorize(Integer userid, String auth) {
        User user = mMapper.findUserById(userid);
        if (user == null ){
            return ResponseUtil.error(ExceptionEnum.HTTP_UNKNOWN);
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            sb.append(";");
        }
        sb.append(auth);
        int authorized = mMapper.authorize(userid, sb.toString());
        if( authorized>0 ){
            //刷新token
            return refreshToken(userid);
        }else {
            return ResponseUtil.error(50001, "授权失败");
        }
    }

    /**
     * 刷新token
     * @param userId
     */
    public Response refreshToken(Integer userId) {
        User user = mMapper.findUserById(userId);
        if (user == null) {
            return ResponseUtil.error(ExceptionEnum.HTTP_TOKEN_ERROR);
        }
        String token = JwtHelper.createJwt(user, mAudience.getClientId(),
                mAudience.getName(), mAudience.getExpiresSecond(),
                mAudience.getBase64Secret());
        return ResponseUtil.success("bearer;" + token);
    }


}









