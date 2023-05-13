package com.qxj.qingxiaojiamaster.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.mapper.AdminMapper;
import com.qxj.qingxiaojiamaster.service.AdminService;
import com.qxj.qingxiaojiamaster.utils.JWTUtils;
import com.qxj.qingxiaojiamaster.utils.MybatisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
    @Resource
    AdminMapper adminMapper;

    /**
     * @param admin
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 管路员用户登录
     * @author hasdsd
     * @Date 2023/4/22
     */
    @PostMapping("/login")
    public R Login(@RequestBody Admin LoginAdmin, HttpServletRequest request) {
        //声明map用于返回所有信息
        HashMap<String, Object> map = new HashMap<>();
        try {
            //登录获取用户
            Admin admin = adminService.Login(LoginAdmin);
            HashMap<String, String> payload = new HashMap<>();
            payload.put("id",String.valueOf(admin.getId()));
            payload.put("number",String.valueOf(admin.getNumber()));
            payload.put("name",admin.getName());
            payload.put("role", String.valueOf(admin.getRole()));
            String token = JWTUtils.getToken(payload);
            map.put("token",token);
            map.put("state",true);
            map.put("msg","登录成功");
        }
        catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getCause());
            e.getStackTrace();
        }


        return R.success("登录成功", map);
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
            @RequestParam(value = "college", required = false) String college
    ) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<Admin>()
                .select(Admin.class, info -> !info.getColumn().equals("password"))
                .like(MybatisUtil.condition(number), Admin::getNumber, number)
                .like(MybatisUtil.condition(name), Admin::getName, name)
                .like(MybatisUtil.condition(college), Admin::getCollege, college)
                .orderByDesc(Admin::getId);
        Page<Admin> pageResult = adminMapper.selectPage(new Page<>(currentPage, pageSize), wrapper);
        return R.page(pageResult.getTotal(), pageResult.getRecords());
    }

    /**
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 获取全部导员信息，用于新增班级
     * @author hasdsd
     * @Date 2023/5/9
     */
    @GetMapping("/getAllAdmin")
    public R getAllAdmin(@RequestParam(value = "collegeId", required = false) Integer collegeId) {
        return R.success(adminService.lambdaQuery()
                .select(Admin::getId, Admin::getName)
                .eq(MybatisUtil.condition(collegeId), Admin::getCollege, collegeId)
                .list());
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
