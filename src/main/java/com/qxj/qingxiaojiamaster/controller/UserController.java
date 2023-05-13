package com.qxj.qingxiaojiamaster.controller;


import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.mapper.UserMapper;
import com.qxj.qingxiaojiamaster.service.UserService;
import com.qxj.qingxiaojiamaster.utils.JWTUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    @Resource
    UserMapper userMapper;

    /**
     * @param user
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 用户登录
     * @author hasdsd
     * @Date 2023/4/22
     */
    @PostMapping("/login")
    public R login(@RequestBody User user, HttpServletRequest request) {
        //声明map用于返回所有信息
        HashMap<String, Object> map = new HashMap<>();
        try {
            //获取用户信息
            User userInfo = userService.Login(user);
            //创建payload
            HashMap<String, String> payload = new HashMap<>();
            payload.put("id",String.valueOf(userInfo.getId()));
            payload.put("number",String.valueOf(userInfo.getNumber()));
            payload.put("name",userInfo.getName());
            //  生成token
            String token = JWTUtils.getToken(payload);
            map.put("token",token);
            map.put("state",true);
            map.put("msg","登录成功");
            //规范!什么规范?
            Map<String, String> otherUserInfo = userMapper.selectOtherUserInfo(userInfo.getId());
            map.put("userInfo", userInfo);
            map.put("details", otherUserInfo);
        }
        catch (Exception e) {
            map.put("state",false);
            map.put("msg",e.getCause());
            e.getStackTrace();
        }

        return R.success(map);
    }
}
