package com.qxj.qingxiaojiamaster.controller;


import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.service.AdminService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/admin")
@RestController
@Slf4j
public class AdminController {
    @Resource
    AdminService adminService;

    /**
     * @param admin
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 管路员用户登录
     * @author hasdsd
     * @Date 2023/4/22
     */
    @PostMapping("/login")
    public R Login(@RequestBody Admin admin) {
        Admin result = adminService.lambdaQuery().eq(Admin::getNumber, admin.getNumber()).one();
        if (result == null) {
            throw new NormalException("账号不存在");
        }
        if (!result.getPassword().equals(admin.getPassword())) {
            throw new NormalException("密码错误");
        }
        result.setPassword("");//除去密码返回
        return R.success("登录成功", result);
    }
}
