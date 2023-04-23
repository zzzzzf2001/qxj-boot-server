package com.qxj.qingxiaojiamaster.web.admin;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.service.AdminService;
import com.qxj.qingxiaojiamaster.service.UserService;
import com.qxj.qingxiaojiamaster.utils.MybatisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/19 22:18
 * 用户管理
 **/

@RestController
@Slf4j
@RequestMapping("/admin/user")
public class UserManagementController {
    /**
     * 搜索用户信息   /select (条件查询，分页查询)
     * 新增用户信息   /add
     * 导入用户信息   /import
     * 导出用户信息   /export
     * 修改用户信息   /modify
     * 删除用户信息   /delete
     * 重置用户信息   /reset
     * 解绑微信号    /unbinding
     */
    @Resource
    AdminService adminService;

    @Resource
    UserService userService;


    /**
     * @param currentPage, pageSize
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 获取全部学生信息
     * @author hasdsd
     * @Date 2023/4/23
     * TODO:查出来的结果问题
     */
    @GetMapping()
    public R getUserInfo(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "number", required = false) String number,
            @RequestParam(value = "classId", required = false) String[] classId,
            @RequestParam(value = "enable", required = false) String enable
    ) {
        List<User> list = userService
                .lambdaQuery()
                .like(MybatisUtil.condition(username), User::getName, username)
                .like(MybatisUtil.condition(number), User::getNumber, number)
                .like(MybatisUtil.condition(enable), User::getEnable, enable)
                .in(MybatisUtil.condition(classId), User::getClassId, MybatisUtil.handleArray(classId))
                .last(MybatisUtil.limitPage(currentPage, pageSize))
                .list();
        return R.success(list);
    }

    /**
     * @param user
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 新增学生信息
     * @author hasdsd
     * @Date 2023/4/23
     */
    @PostMapping()
    public R insertUser(@RequestBody User user) {
        try {
            user.setCrateTime(LocalDateTime.now());
            userService.save(user);
        } catch (Exception e) {
            throw new NormalException("新增用户错误", e);
        }
        return R.success();
    }


    /**
     * @param userId
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 软删除用户
     * @author hasdsd
     * @Date 2023/4/23
     */
    @DeleteMapping()
    public R deleteUser(@RequestParam("userId") Integer userId) {
        userService.updateById(User.builder().id(userId).enable(0).build());
        return R.success();
    }


    /**
     * @param user
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 修改用户信息
     * @author hasdsd
     * @Date 2023/4/23
     */
    @PutMapping()
    public R updateInfo(@RequestBody User user) {
        try {
            userService.updateById(user);
        } catch (Exception e) {
            throw new NormalException("修改失败", e);
        }
        return R.success();
    }

    /**
     * @param currentPage, pageSize
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 获取全部管理员信息
     * @author hasdsd
     * @Date 2023/4/23
     */
    @GetMapping("/admins")
    public R getAdminInfo(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize
    ) {
        List<Admin> list = adminService.lambdaQuery().last(MybatisUtil.limitPage(currentPage, pageSize)).list();
        return R.success(list);
    }
}
