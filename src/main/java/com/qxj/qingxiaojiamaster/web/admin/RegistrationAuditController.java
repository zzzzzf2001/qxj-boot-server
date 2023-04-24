package com.qxj.qingxiaojiamaster.web.admin;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.service.ClassService;
import com.qxj.qingxiaojiamaster.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/19 21:45
 * 注册审批管理
 **/





@RestController
@RequestMapping("/admin/registry")
public class RegistrationAuditController {

    @Resource
    private UserService userService;


    /**
     * todo
     * 对于注册信息的审核  /audit
    \

     *  查看注册信息      /show
      **/

    public R selectRegistry(@RequestBody Admin admin,
                            @RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "number",required = false) String number,
                            @RequestParam(value = "enable",required = false) int enable,
                            @RequestParam(value = "create_table",required = false)LocalDateTime create_time,
                            @RequestParam(value = "collegeName",required = false) String  college,
                            @RequestParam(value = "majorName",required = false) String major,
                            @RequestParam(value = "className",required = false) String className,
                            @RequestParam(value = "currentPage",required = false) int currentPage,
                            @RequestParam(value = "pageSize",required = false) int pageSize
                            ){
        userService.getRegistryUser(admin,name, number, enable, create_time, college, major,className,currentPage,pageSize);

        return null;
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
