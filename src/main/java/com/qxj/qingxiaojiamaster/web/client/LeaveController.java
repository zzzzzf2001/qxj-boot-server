package com.qxj.qingxiaojiamaster.web.client;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qxj.qingxiaojiamaster.common.Constants;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Order;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.entity.dto.LeaveCommitDto;
import com.qxj.qingxiaojiamaster.mapper.OrderMapper;
import com.qxj.qingxiaojiamaster.mapper.OrderStatusMapper;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import com.qxj.qingxiaojiamaster.service.UserService;
import org.springframework.beans.BeanUtils;
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
    OrderMapper orderMapper;
@Resource
    OrderStatusService orderStatusService;

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
    @PostMapping("/commit/{leaveCommitDto}")
    public R LeaveCommit(@PathVariable("leaveCommitDto") LeaveCommitDto  leaveCommitDto){
        boolean save = orderService.LeaveCommit(leaveCommitDto);

        if (save) {
            return R.success("提交成功");
        }
        else {
            return R.error(Constants.CODE_400,"提交失败，请重试");
        }

    }
    /**
     * 列出所有请假表
     */

    @PostMapping("/selectAll")
    public R selectALl(int id){
        List<LeaveCommitDto> leaveCommitDtos = orderService.selectAllOrderInfo(id);


        return  R.success(leaveCommitDtos);
    }


    /**
    销假
     根据假条ID销假
     **/
    @PostMapping("/cancel")
    public R  cancelLeave(int id){
        boolean result = orderStatusService.cancelLeave(id);
        if (result==true){
        return R.success();
        }
        return R.error();
    }




    /**
    修改假条信息（只可去往地，开始时间，结束时间）
    查询请假记录（审批状态，销假状态）
    取消请假
        **/
}