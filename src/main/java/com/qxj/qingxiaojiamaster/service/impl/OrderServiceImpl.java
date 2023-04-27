package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.Class;
import com.qxj.qingxiaojiamaster.entity.Order;
import com.qxj.qingxiaojiamaster.entity.OrderStatus;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.mapper.OrderMapper;
import com.qxj.qingxiaojiamaster.mapper.OrderStatusMapper;
import com.qxj.qingxiaojiamaster.mapper.UserMapper;
import com.qxj.qingxiaojiamaster.service.ClassService;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import com.qxj.qingxiaojiamaster.service.UserService;
import com.qxj.qingxiaojiamaster.utils.MybatisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderStatusService orderStatusService;
    @Resource
    OrderStatusMapper orderStatusMapper;
    @Resource
    OrderService orderService;

    @Resource
    UserService userService;

    @Resource
    UserMapper userMapper;

    @Resource
    ClassService classService;


    @Transactional
    @Override
    // 提交 请假请求
    public boolean LeaveCommit(Order order, Integer userId) {
        //将请假表进行保存
        order.setUserId(userId);
        log.info(order.toString());
        boolean result1 = this.save(order);
        //判断是否保存成功
        if (result1 == false) {
            return false;
        }
        return true;
    }

    @Override
    public boolean setStatus(Order order, Integer userid) {
        //查询请假表
        Order order2 = orderService.lambdaQuery()
                .eq(Order::getFromTime,order.getFromTime())
                .eq(Order::getToTime,order.getToTime())
                .eq(Order::getUserId,userid)
                .one();
        //创建请假状态类
        OrderStatus orderStatus = new OrderStatus();
        //填入用户名和请假表ID
        orderStatus.setUserId(userid);
        orderStatus.setCreateTime(LocalDateTime.now());
        orderStatus.setOrderId(order2.getId());
        boolean result = orderStatusService.save(orderStatus);
        return result;
    }





    @Override
    public R selectOrderByStatus(User user, Integer currentPage, Integer pageSize, int status) {
        //利用条件查询其状态对象 参数为该用户的状态以及用户ID
        List<OrderStatus> list = orderStatusService.lambdaQuery()
                .eq(OrderStatus::getUserId, user.getId())
                .eq(StringUtils.isNotBlank(String.valueOf(status)), OrderStatus::getStatus, status)
                .list();
        //将请假条ID从状态集合中取出，并封装到数组中
        List<Integer> orderIds = new ArrayList<>();
        for (OrderStatus orderStatus : list) {
            Integer orderId = orderStatus.getOrderId();
            if (!orderIds.contains(orderId))
                orderIds.add(orderId);
        }
        log.info(orderIds.toString());
        //根据请假条ID查询假条集合
        List<Order> orders = orderService.lambdaQuery()
                .in(Order::getId, orderIds)
                .list();

        return R.success(orders);

    }

    @Override
    public R selectOrderByTable(Admin admin, Integer classId, String userName, String userNumber, Integer status, LocalDateTime fromTime, LocalDateTime toTime, Integer currentPage, Integer pageSize) {
        List<User> users = userService.getRegistryUser(admin, userName, userNumber, status, null, null, classId, null, null);
        ArrayList<Integer> uids=new ArrayList<>();

        for(User user:users){
            uids.add(user.getId());
        }







        return null;
    }

}



