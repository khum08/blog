package com.yzk.controller;

import com.yzk.model.Response;
import com.yzk.model.domain.Audience;
import com.yzk.model.domain.User;
import com.yzk.service.UserService;
import com.yzk.util.JwtHelper;
import com.yzk.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/9/9
 *     desc   :
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
        String token = JwtHelper.createJwt(queryUer.getUsername(), queryUer.getId(),
                queryUer.getAuthorities().toString(), mAudience.getClientId(),
                mAudience.getName(), mAudience.getExpiresSecond(),
                mAudience.getBase64Secret());

        return ResponseUtil.success("bearer;" + token);
    }

    @PostMapping("/register")
    public Response register(@RequestBody User user) {
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


}
