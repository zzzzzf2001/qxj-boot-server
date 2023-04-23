package com.qxj.qingxiaojiamaster.web.client;


import com.qxj.qingxiaojiamaster.common.Constants;
import com.qxj.qingxiaojiamaster.common.PageParams;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Order;

import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.entity.dto.LeaveCommitDto;
import com.qxj.qingxiaojiamaster.mapper.OrderStatusMapper;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import com.qxj.qingxiaojiamaster.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/22 09:23
 **/

@RestController
@RequestMapping("/user")
public class LeaveController {

@Resource
    UserService userService;

@Resource
    OrderService orderService;

@Resource
    OrderStatusService orderStatusService;

@Resource
    OrderStatusMapper orderStatusMapper;

/**
 *
 * 提交证明材料
 *
 * **/





    /**
     提交请假信息
     参数: LeaveCommitDto
    **/
    @Transactional
    @PostMapping("/commit")
    public R LeaveCommit(@RequestBody LeaveCommitDto  leaveCommitDto){
        boolean save = orderService.LeaveCommit(leaveCommitDto);

        if (save) {
            return R.success("提交成功");
        }
        else {
            return R.error(Constants.CODE_400,"提交失败，请重试");
        }

    }


    /**
     * @param id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 请求销假表
     * @author 15754
     * @Date 2023/4/23
     */
    @Transactional
    @GetMapping("/cancel/{id}")
    public R  cancelLeave(@PathVariable("id") int id){
        boolean result = orderStatusService.cancelLeave(id);
        if (result==true){
        return R.success();
        }
        return R.error();
    }




    /**
     * @param user, pageSize,currentPage,status
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 查询所有请假表
     * @author 15754
     * @Date 2023/4/23
     */
    @GetMapping("/showLeave/{status}")
    public R showOrder( @RequestBody User user,
                        @RequestParam("currentPage") Integer currentPage,
                        @RequestParam("pageSize") Integer pageSize,
                        @PathVariable("status") int status){

        return orderService.selectOrderByStatus(user,currentPage,pageSize,status);
    }

    /**
    取消请假
     **/




    /**
    修改假条信息（只可去往地，开始时间，结束时间）
     **/







     /**
     查询请假记录（审批状态，销假状态）

        **/
}
