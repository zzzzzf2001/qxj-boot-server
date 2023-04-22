package com.qxj.qingxiaojiamaster.service.impl;

import com.qxj.qingxiaojiamaster.entity.Order;
import com.qxj.qingxiaojiamaster.entity.OrderStatus;
import com.qxj.qingxiaojiamaster.entity.dto.LeaveCommitDto;
import com.qxj.qingxiaojiamaster.mapper.OrderMapper;
import com.qxj.qingxiaojiamaster.mapper.OrderStatusMapper;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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


    @Override

    // 提交 请假请求
    public boolean LeaveCommit(LeaveCommitDto leaveCommitDto) {
        //创建请假类对象
        Order order = new Order();
        //将DTO赋值给请假类对象
        BeanUtils.copyProperties(leaveCommitDto,order);
        //将请假表进行保存
        boolean result1 = save(order);
        //判断是否保存成功
        if (result1==false){
           return false;
       }
        //将刚刚存入的请假表取出（因为Order的ID为自增，保存后才会有ID）
        Order order2 = orderMapper.selectById(leaveCommitDto.getId());
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

    @Override
    public List<LeaveCommitDto> selectAllOrderInfo(int id) {
        List<LeaveCommitDto> orderAll = new ArrayList<>();
        List<Order> orders = orderMapper.selectOrderByUserID(id);
        for(Order order:orders){
             /***
              *
              *     这里没写对先空着
              *
              * */
            OrderStatus status = orderStatusMapper.getStatusByorderID(order.getId());
            BeanUtils.copyProperties(order,new LeaveCommitDto());
        }
        return null;
    }


}
