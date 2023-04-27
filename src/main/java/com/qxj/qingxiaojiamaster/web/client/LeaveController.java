package com.qxj.qingxiaojiamaster.web.client;


import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Order;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.mapper.OrderStatusMapper;
import com.qxj.qingxiaojiamaster.model.PageResult;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import com.qxj.qingxiaojiamaster.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.qxj.qingxiaojiamaster.common.Constants.CODE_200;
import static com.qxj.qingxiaojiamaster.common.Constants.CODE_400;


/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/22 09:23
 **/
@Slf4j
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
     * @param order,user
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 新建请假
     * @author 15754
     * @Date 2023/4/23
     */
    @Transactional
    @PostMapping("/commit")
    public R LeaveCommit(@RequestBody Order order, @RequestParam("userId") int userId) {
        order.setCreateTime(LocalDateTime.now());
        boolean haveCommit = orderStatusService.haveCommit(userId);
        if (haveCommit==true) return R.error(CODE_400,"您已提交过请假信息了，请勿多次提交");
        try {
            orderService.LeaveCommit(order, userId);

        } catch (Exception e) {
            throw new NormalException("添加失败", e);
        }
        finally {
            orderService.setStatus(order, userId);
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
    public R backLeave(@PathVariable("id") int id) {

        try {
            orderStatusService.backLeave(id);
        } catch (Exception e) {
            throw new NormalException("销假请求发送失败", e);
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
    @PostMapping("/showLeave/{status}")
    public R showOrder(@RequestBody User user,
                       @RequestParam(value = "currentPage",required = false) Integer currentPage,
                       @RequestParam(value = "pageSize",required = false) Integer pageSize,
                       @PathVariable(value = "status",required = false)  int status) {
        PageResult<Order> orderPageResult = orderService.selectOrderByStatus(user, currentPage, pageSize, status);
        return orderPageResult!=null? R.success(orderPageResult):R.error(CODE_200,"查询不到此状态假条，请重试");
    }

    /**
     * @param id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 请求销假
     * @author 15754
     * @Date 2023/4/23
     */
    @GetMapping("/cancel/{id}")
    public R cancelLeave(@PathVariable("id") int id) {
        try {
            orderStatusService.cancelLeave(id);
        } catch (Exception e) {
            throw new NormalException("取消请假发送失败", e);
        }
        return R.success();
    }


    /**
     修改假条信息（只可去往地，开始时间，结束时间）
     **/


}
