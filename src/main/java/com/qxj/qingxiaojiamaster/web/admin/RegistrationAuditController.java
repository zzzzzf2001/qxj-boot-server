package com.qxj.qingxiaojiamaster.web.admin;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.service.ClassService;
import com.qxj.qingxiaojiamaster.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
     * todo
     * 对于注册信息的审核  /audit
    \

     /**
     * @param   admin,name,numer,enable,create_time,classId,currentPage,pageSize
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 新增学生信息
     * @author 15754
     * @Date 2023/4/24
     */
    @GetMapping("/select")
    public R selectRegistry(@RequestBody Admin admin,
                            @RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "number",required = false) String number,
                            @RequestParam(value = "enable",required = false) Integer enable,
                            @RequestParam(value = "create_table",required = false)LocalDateTime create_time,
                            @RequestParam(value = "class_id" ,required = false) Integer classId,
                            @RequestParam(value = "currentPage",required = false) Integer currentPage,
                            @RequestParam(value = "pageSize",required = false) Integer pageSize

    ){



        return R.success(userService.getRegistryUser(admin, name, number, enable,create_time, classId, currentPage, pageSize));
    }

     /**
     * @param
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 新增学生信息
     * @author 15754
     * @Date 2023/4/24
     */



    /**
     *
     *
     *
     *  导出初测信息      /export
     *
     */

}
