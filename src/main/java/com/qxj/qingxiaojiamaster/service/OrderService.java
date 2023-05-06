package com.qxj.qingxiaojiamaster.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.Order;
import com.qxj.qingxiaojiamaster.entity.User;

import java.time.LocalDateTime;
import java.util.List;

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


    R getOrderPage(Admin admin, String name, String number, Integer status, LocalDateTime createTime, LocalDateTime toTime, Integer classId, Integer currentPage, Integer pageSize);

    R softDeleteOne(Integer id);

    R softDeleteBatch(List<Integer> ids);

    R approvalOrder(Integer id, Integer agree);
}
