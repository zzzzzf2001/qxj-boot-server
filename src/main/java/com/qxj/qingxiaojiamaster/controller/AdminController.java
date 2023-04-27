package com.qxj.qingxiaojiamaster.controller;


import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.service.AdminService;
import com.qxj.qingxiaojiamaster.utils.MybatisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
        Admin result = adminService.Login(admin);
        return R.success("登录成功", result);
    }

    /**
     * @param number, name, collegeId
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 获取导员信息
     * @author hasdsd
     * @Date 2023/4/24
     */
    @GetMapping()
    public R getAdminInfo(
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam(value = "number", required = false) String number,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "collegeId", required = false) String[] collegeId
    ) {
        List<Admin> admins = adminService.lambdaQuery()
                .select(Admin.class, info -> !info.getColumn().equals("password"))
                .like(MybatisUtil.condition(number), Admin::getNumber, number)
                .like(MybatisUtil.condition(name), Admin::getName, name)
                .like(MybatisUtil.condition(collegeId), Admin::getName, collegeId)
                .last(MybatisUtil.limitPage(currentPage, pageSize))
                .list();
        return R.success(admins);
    }

    /**
     * @param admin
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 新增信息
     * @author hasdsd
     * @Date 2023/4/24
     */
    @PostMapping()
    public R insertInfo(@RequestBody Admin admin) {
        try {
            adminService.save(admin);
        } catch (Exception e) {
            throw new NormalException("新增异常", e);
        }
        return R.success();
    }


    /**
     * @param admin
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 更新信息
     * @author hasdsd
     * @Date 2023/4/24
     */
    @PutMapping()
    public R updateInfo(@RequestBody Admin admin) {
        try {
            adminService.updateById(admin);
        } catch (Exception e) {
            throw new NormalException("更新错误", e);
        }
        return R.success();
    }

    /**
     * @param adminId,enable
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 删除或恢复用户
     * @author hasdsd
     * @Date 2023/4/24
     */
    @DeleteMapping()
    public R deleteUser(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("enable") Integer enable
    ) {
        return R.success(adminService.updateById(Admin.builder().id(adminId).enable(enable).build()));
    }
}
