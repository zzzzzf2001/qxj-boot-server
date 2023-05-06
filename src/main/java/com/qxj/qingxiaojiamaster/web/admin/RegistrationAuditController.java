package com.qxj.qingxiaojiamaster.web.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.entity.dto.UserDetails;
import com.qxj.qingxiaojiamaster.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.qxj.qingxiaojiamaster.common.Constants.CODE_200;
import static com.qxj.qingxiaojiamaster.common.Constants.CODE_500;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/19 21:45
 * 注册审批管理
 **/


@Slf4j
@RestController
@RequestMapping("/admin/registry")
public class RegistrationAuditController {

    @Resource
    private UserService userService;


    /**
     * @param user
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 查询学生详细信息
     * @author 15754
     * @Date 2023/4/26
     */


    @PostMapping("/show")
    public R showDetail(@RequestBody User user) {
        Integer userId = user.getId();
        UserDetails userDetail = userService.getUserDetail(userId);
        return userDetail != null ? R.success(userDetail) : R.error(CODE_200, "系统出现异常");
    }


    /**
     * @param user,enable
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 审批注册信息
     * @author 15754
     * @Date 2023/4/25
     */
    @Transactional
    @PostMapping("/audit/{enable}")
    public R aduitRegistry(@RequestBody User user,
                           @PathVariable("enable") Integer enable
    ) {
        boolean result = userService.update(new
                LambdaUpdateWrapper<User>()
                .eq(User::getId, user.getId())
                .set(User::getEnable, enable)
        );

        return result ? R.success() : R.error(CODE_500, "审批失败请稍后重试");
    }


    /**
     * @param admin,name,numer,enable,create_time,classId,currentPage,pageSize
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 查询注册学生信息
     * @author 15754
     * @Date 2023/4/24
     */
    @PostMapping("/select")
    public R selectRegistry(@RequestBody Admin admin,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "number", required = false) String number,
                            @RequestParam(value = "enable", required = false) Integer enable,
                            @RequestParam(value = "create_table", required = false) LocalDateTime create_time,
                            @RequestParam(value = "to_time", required = false) LocalDateTime to_time,
                            @RequestParam(value = "class_id", required = false) Integer classId,
                            @RequestParam(value = "currentPage", required = false) Integer currentPage,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        return userService.getRegistryUser(admin, name, number, enable, create_time, to_time, classId, currentPage, pageSize);
    }

    /**
     *
     *
     *
     *  导出初测信息      /export
     *
     */


}
