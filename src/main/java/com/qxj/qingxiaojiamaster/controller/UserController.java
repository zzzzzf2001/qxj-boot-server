package com.qxj.qingxiaojiamaster.controller;


import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.service.UserService;
import com.qxj.qingxiaojiamaster.utils.LoginUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody User user) {
        User result = userService.lambdaQuery().eq(User::getNumber, user.getNumber()).one();
        if (result == null) {
            throw new NormalException("账号不存在");
        }
        //检测是否正确
        LoginUtil.checkAccount(user.getPassword(), result.getPassword());
        return R.success();
    }
}
