
package com.qxj.qingxiaojiamaster.web.admin;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/19 22:02
 * 请假审批管理
 **/

@RestController
@RequestMapping("/admin/leave")
public class LeaveReviewController {


    @Resource
    private OrderService orderService;




    /*
       搜索请假信息      /select (条件查询,分页查询
       **/


    public R select(
            @RequestBody Admin admin,
            @RequestParam("classId") Integer classId,
            @RequestParam("userName") String userName,
            @RequestParam("userNumber") String userNumber,
            @RequestParam("status") Integer status,
            @RequestParam("from_time")LocalDateTime fromTime,
            @RequestParam("to_time") LocalDateTime toTime,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ){
        orderService.selectOrderByTable(admin,classId,userName,userNumber,status,fromTime,toTime,currentPage,pageSize);

    return new R();
    }





    /**
       修改请假信息      /modify
       删除请假信息      /delete
       审核请假信息      /audit
       查看请假详情      /show
       批量删除请假信息   /deleteList
       导出请假信息      /export
     */





}
