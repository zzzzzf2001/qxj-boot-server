package com.qxj.qingxiaojiamaster.web.admin;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.AllStudentInfo;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.service.AdminService;
import com.qxj.qingxiaojiamaster.service.IAllStudentInfoService;
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

    @Resource
    IAllStudentInfoService allStudentInfoService;

    /**
     * @param currentPage, pageSize
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 获取全部学生信息
     * @author hasdsd
     * @Date 2023/4/23
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
        List<AllStudentInfo> list = allStudentInfoService
                .lambdaQuery()
                .select(AllStudentInfo.class, info -> !info.getColumn().equals("password"))
                .like(MybatisUtil.condition(username), AllStudentInfo::getName, username)
                .like(MybatisUtil.condition(number), AllStudentInfo::getNumber, number)
                .like(MybatisUtil.condition(enable), AllStudentInfo::getEnable, enable)
                .in(MybatisUtil.condition(classId), AllStudentInfo::getClassName, (Object[]) classId)
                .orderBy(true, false, AllStudentInfo::getCreateTime)
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
     * @Description 删除或恢复用户
     * @author hasdsd
     * @Date 2023/4/23
     */
    @DeleteMapping()
    public R deleteUser(@RequestParam("userId") Integer userId, @RequestParam("enable") Integer enable) {
        userService.updateById(User.builder().id(userId).enable(enable).build());
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

}
