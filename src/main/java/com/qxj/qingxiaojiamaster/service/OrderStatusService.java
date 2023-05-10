package com.qxj.qingxiaojiamaster.service;

import com.qxj.qingxiaojiamaster.entity.OrderStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
public interface OrderStatusService extends IService<OrderStatus> {

    boolean backLeave(int id);

    boolean cancelLeave(int id);


    boolean haveCommit(int userId);

    boolean expireOrder(List<Integer> orderIds);
}
