package com.qxj.qingxiaojiamaster.service;

import com.qxj.qingxiaojiamaster.common.PageParams;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.entity.dto.LeaveCommitDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
public interface OrderService extends IService<Order> {

    boolean LeaveCommit(LeaveCommitDto leaveCommitDto);

    R selectOrderByStatus(User user, Integer currentPage, Integer pageSize, int status);

}
