package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qxj.qingxiaojiamaster.entity.OrderStatus;
import com.qxj.qingxiaojiamaster.mapper.OrderStatusMapper;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.utils.MybatisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class OrderStatusServiceImpl extends ServiceImpl<OrderStatusMapper, OrderStatus> implements OrderStatusService {
    @Resource
    OrderStatusMapper orderStatusMapper;

    @Override
    public boolean backLeave(int id) {
        int status=4;
        boolean result = orderStatusMapper.setStatusNew(id,status);
        return result;
    }

    @Override
    public boolean cancelLeave(int id) {
        int status=9;
        boolean result = orderStatusMapper.setStatusNew(id,status);
        return result;
    }

    @Override
    public boolean haveCommit(int userId) {
        OrderStatus haveCommit = orderStatusMapper.haveCommit(userId);
        return MybatisUtil.condition(haveCommit);
    }

    @Override
    public boolean expireOrder(List<Integer> orderIds) {
        LambdaUpdateWrapper<OrderStatus> set = new LambdaUpdateWrapper<OrderStatus>()
                .eq(OrderStatus::getStatus, 1)
                .in(OrderStatus::getOrderId, orderIds)
                .set(OrderStatus::getStatus, 2);
        return this.update(set);
    }
}
