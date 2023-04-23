package com.qxj.qingxiaojiamaster.web.client;



import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.entity.dto.LeaveCommitDto;
import com.qxj.qingxiaojiamaster.mapper.OrderStatusMapper;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import com.qxj.qingxiaojiamaster.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


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
     * @param leaveCommitDto
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 请求销假
     * @author 15754
     * @Date 2023/4/23
     */
    @Transactional
    @PostMapping("/commit")
    public R LeaveCommit(@RequestBody LeaveCommitDto  leaveCommitDto){


        try{
            orderService.LeaveCommit(leaveCommitDto);
        }
        catch (Exception e){
            throw new NormalException("添加失败",e);
        }
        return R.success("添加成功");
    }


    /**
     * @param id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 请求销假
     * @author 15754
     * @Date 2023/4/23
     */
    @Transactional
    @GetMapping("/back/{id}")
    public R  backLeave(@PathVariable("id") int id){

       try {
           orderStatusService.backLeave(id);
       }
       catch (Exception e){
           throw new NormalException("销假请求发送失败",e);
       }
       return R.success();
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
     * @param id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 请求销假
     * @author 15754
     * @Date 2023/4/23
     */
    @GetMapping("/cancel/{id}")
    public R cancelLeave(@PathVariable("id") int id){
        try {
            orderStatusService.cancelLeave(id);
        }
        catch (Exception e){
            throw new NormalException("取消请假发送失败",e);
        }
        return R.success();
    }



    /**
    修改假条信息（只可去往地，开始时间，结束时间）
     **/



}
