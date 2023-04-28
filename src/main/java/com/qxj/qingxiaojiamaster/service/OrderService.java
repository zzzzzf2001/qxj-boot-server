package com.qxj.qingxiaojiamaster.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Order;
import com.qxj.qingxiaojiamaster.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
public interface OrderService extends IService<Order> {

    boolean LeaveCommit(Order order, Integer userid);

    boolean setStatus(Order order, Integer userid);

    R selectOrderByStatus(User user, Integer currentPage, Integer pageSize, int status);


}
