package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.model.domain.Audience;
import com.yzk.model.domain.User;
import com.yzk.service.UserService;
import com.yzk.util.JwtHelper;
import com.yzk.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   :
 * </pre>
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserService mService;
    @Autowired
    Audience mAudience;

    @PostMapping("/login")
    public Response login(@RequestParam(value = "username", required = true) String username,
                          @RequestParam(value = "password", required = true) String password,
                          HttpServletRequest request) {
        User user = new User();
        user.setUserName(username);
        User queryUer = mService.login(user);
        if (queryUer == null) {
            return ResponseUtil.error(40000,"该用户不存在");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(password, queryUer.getPassword());
        if (!matches){
            return ResponseUtil.error(40001, "密码错误");
        }
        String token = JwtHelper.createJwt(queryUer.getUserName(), queryUer.getId(),
                queryUer.getAuthorities().toString(), mAudience.getClientId(),
                mAudience.getName(), mAudience.getExpiresSecond(),
                mAudience.getBase64Secret());
        return ResponseUtil.success("bearer;" + token);
    }


}
