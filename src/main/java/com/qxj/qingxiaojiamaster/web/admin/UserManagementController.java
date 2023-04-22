package com.qxj.qingxiaojiamaster.web.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/19 22:18
 * 用户管理
 **/

@RestController
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
     *
     */

}
