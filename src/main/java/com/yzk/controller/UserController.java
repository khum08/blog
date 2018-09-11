package com.yzk.controller;

import com.yzk.exception.ExceptionEnum;
import com.yzk.model.Response;
import com.yzk.model.domain.Audience;
import com.yzk.model.domain.User;
import com.yzk.service.UserService;
import com.yzk.util.JwtHelper;
import com.yzk.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   : 用户管理
 * </pre>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService mService;
    @Autowired
    Audience mAudience;
    @Autowired
    PasswordEncoder mPasswordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 表单提交
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Response login(@RequestParam(value = "username", required = true) String username,
                          @RequestParam(value = "password", required = true) String password
                          ) {
        User user = new User();
        user.setUsername(username);
        User queryUer = mService.findUserByUsername(user);
        if (queryUer == null) {
            return ResponseUtil.error(40000,"该用户不存在");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(password, queryUer.getPassword());
        if (!matches){
            return ResponseUtil.error(40001, "密码错误");
        }
        String token = JwtHelper.createJwt(queryUer, mAudience.getClientId(),
                mAudience.getName(), mAudience.getExpiresSecond(),
                mAudience.getBase64Secret());

        return ResponseUtil.success("bearer;" + token);
    }

    @PostMapping("/register")
    public Response register(@RequestBody User user) {
        user.setAuthorities("READER;");
        logger.debug(user.toString());
        String password = mPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        int success = mService.register(user);
        switch (success){
            case -1:
                return ResponseUtil.error(40002, "该用户已存在");
            case 0:
                return ResponseUtil.error(40003, "注册失败");
            case 1:
                return ResponseUtil.success();
            default:
                return ResponseUtil.error(40003, "注册失败");
        }
    }

    /**
     * www-form-urlencoded传递参数auth
     * @param auth
     * @param request
     * @return
     */
    @PutMapping("/authorize")
    public Response authorize(@RequestParam(value = "auth" ,required = true) String auth,
                              HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        Integer userid = (Integer) claims.get("userid");
        if (userid == null) {
            return ResponseUtil.error(ExceptionEnum.HTTP_UNKNOWN);
        }
        return mService.authorize(userid, auth.toUpperCase());
    }

    /**
     * 刷新token
     * @param request
     * @return
     */
    @GetMapping("/refresh_token")
    public Response refreshToken(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        Integer userid = (Integer) claims.get("userid");
        return mService.refreshToken(userid);
    }

}
