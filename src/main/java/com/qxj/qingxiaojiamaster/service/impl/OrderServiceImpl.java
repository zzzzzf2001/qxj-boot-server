package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxj.qingxiaojiamaster.common.PageParams;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Order;
import com.qxj.qingxiaojiamaster.entity.OrderStatus;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.entity.dto.LeaveCommitDto;
import com.qxj.qingxiaojiamaster.mapper.OrderMapper;
import com.qxj.qingxiaojiamaster.mapper.OrderStatusMapper;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import com.qxj.qingxiaojiamaster.utils.MybatisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderStatusService orderStatusService;
    @Resource
    OrderStatusMapper orderStatusMapper;
    @Resource
    OrderService orderService;

    @Override

    // 提交 请假请求
    public boolean LeaveCommit(Order order,User user) {
        //将请假表进行保存
        order.setUserId(user.getId());
        boolean result1 = this.save(order);
        //判断是否保存成功
        if (result1==false){
           return false;
       }
        //将刚刚存入的请假表取出（因为Order的ID为自增，保存后才会有ID）
        Order order2 = orderService.lambdaQuery()
                .eq(Order::getCreateTime, order.getCreateTime())
                .one();
        //创建请假表状态类
        OrderStatus orderStatus=new OrderStatus();
        //请假表默认状态为1
        orderStatus.setStatus(1);
        orderStatus.setOrderId(order2.getId());
        orderStatus.setUserId(order2.getUserId());
        //将请假类保存入数据库
        boolean result2 = orderStatusService.save(orderStatus);

        return result1&&result2;
    }



//    @Override
//    public R selectOrderByStatus(PageParams pageParams, int status) {
//        //通过请假单状态找到所有请假表的ID
//        List<Integer> orderids = orderStatusMapper.selectOrderidByStatus(status);
//        //创建条件查询器
//        LambdaQueryWrapper<Order> queryWrapper=new LambdaQueryWrapper<>();
//        //添加查询条件 倘若id为空，则会直接查询 ，不为空就按条件查询 orderid in (orderids)
//        queryWrapper.in(!orderids.isEmpty(),Order::getId,orderids);
//        //分页查询，创建分页类
//        Page<Order> page = new Page<Order>(pageParams.getCurrentPage(),pageParams.getPageSize());
//        //分页查询返回页结果
//        Page<Order> orderPage1 = orderMapper.selectPage(page, queryWrapper);
//        return R.success(orderPage1);
//    }

    @Override
    public R selectOrderByStatus(User user, Integer currentPage, Integer pageSize, int status) {
        //利用条件查询其状态对象 参数为该用户的状态以及用户ID
        List<OrderStatus> list = orderStatusService.lambdaQuery()
                .eq(OrderStatus::getUserId, user.getId())
                .eq(StringUtils.isNotBlank(String.valueOf(status)), OrderStatus::getStatus, status)
                .list();
        //将请假条ID从状态集合中取出，并封装到数组中
        List<Integer> orderIds=new ArrayList<>();
        for(OrderStatus orderStatus:list){
            Integer orderId = orderStatus.getOrderId();
            if (orderIds.contains(orderId))
                orderIds.add(orderId);
        }
        //根据请假条ID查询假条集合
        List<Order> orders = orderService.lambdaQuery()
                .in(Order::getId, orderIds)
                .last(MybatisUtil.limitPage(currentPage, pageSize))
                .list();

        return R.success(orders);

    }

}
