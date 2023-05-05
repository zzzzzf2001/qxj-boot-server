package com.qxj.qingxiaojiamaster.web.admin;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.Order;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.qxj.qingxiaojiamaster.service.UserService;
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
    private UserService userService;
    @Resource
    private OrderService orderService;


    /*
       搜索请假信息      /select (条件查询,分页查询

        **/
          /**
     * @param admin,name,numer,enable,create_time,classId,currentPage,pageSize
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 查询请假信息
     * @author 15754
     * @Date 2023/4/24
     */
        @GetMapping("/select")
        public R selectLeave(
                @RequestBody Admin admin,
                @RequestParam(value = "name", required = false) String name,
                @RequestParam(value = "number", required = false) String number,
                @RequestParam(value ="status" ,required = false) Integer status,
                @RequestParam(value = "create_table", required = false) LocalDateTime create_time,
                @RequestParam(value = "to_time", required = false) LocalDateTime to_time,
                @RequestParam(value = "class_id", required = false) Integer classId,
                @RequestParam(value = "currentPage", required = false) Integer currentPage,
                @RequestParam(value = "pageSize", required = false) Integer pageSize

        ){
            return orderService.getOrderPage(admin,name,number,status,create_time,to_time,classId,currentPage,pageSize);
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
