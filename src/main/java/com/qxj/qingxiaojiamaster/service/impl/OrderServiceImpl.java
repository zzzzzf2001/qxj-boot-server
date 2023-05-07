package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.*;
import com.qxj.qingxiaojiamaster.entity.dto.OrderBaseDTO;
import com.qxj.qingxiaojiamaster.mapper.AllStudentInfoMapper;
import com.qxj.qingxiaojiamaster.mapper.OrderMapper;
import com.qxj.qingxiaojiamaster.model.PageParams;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import com.qxj.qingxiaojiamaster.service.UserService;
import com.qxj.qingxiaojiamaster.utils.MybatisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.qxj.qingxiaojiamaster.common.Constants.CODE_400;

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
    OrderService orderService;

    @Resource
    UserService userService;

    @Resource
    AllStudentInfoMapper allStudentInfoMapper;


    @Transactional
    @Override
    // 提交 请假请求
    public boolean LeaveCommit(Order order, Integer userId) {
        //将请假表进行保存
        order.setUserId(userId);
        log.info(order.toString());
        //判断是否保存成功
        return this.save(order);
    }

    @Override
    public boolean setStatus(Order order, Integer userid) {
        //查询请假表
        Order order2 = orderService.lambdaQuery()
                .eq(Order::getFromTime, order.getFromTime())
                .eq(Order::getToTime, order.getToTime())
                .eq(Order::getUserId, userid)
                .one();
        //创建请假状态类
        OrderStatus orderStatus = new OrderStatus();
        //填入用户名和请假表ID
        orderStatus.setUserId(userid);
        orderStatus.setCreateTime(LocalDateTime.now());
        orderStatus.setOrderId(order2.getId());
        return orderStatusService.save(orderStatus);
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
            orderIds.add(orderId);
        }

        //创建参数页类
        PageParams pageParams = new PageParams();

        if (MybatisUtil.condition(currentPage) && MybatisUtil.condition(pageSize)) {
            //二者皆为非空才可以设置值
            pageParams.setPageSize(pageSize);
            pageParams.setCurrentPage(currentPage);
        }
        //将每页的条件封装到page对象中
        Page<Order> page = new Page<>(pageParams.getCurrentPage(), pageParams.getPageSize());


        //将假条ID条件封装到其中
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<Order>()
                .in(Order::getId, orderIds);
        //根据页条件和假条条件查询页信息
        Page<Order> orderPage = orderMapper.selectPage(page, orderQueryWrapper);

        List<Order> records = orderPage.getRecords();

        long total = orderPage.getTotal();


        return R.page(total, records);

    }

    @Override
    public R getOrderPage(Admin admin, String name, String number, Integer status, LocalDateTime create_time, LocalDateTime totime, Integer classId, Integer currentPage, Integer pageSize) {

        R registryUser = userService.getRegistryUser(admin, name, number, null, null, null, classId, null, null);
        List<User> userList = (List<User>) registryUser.getData();
        log.info("-------****************--------------"+userList.toString());
        List<Integer> ids = new ArrayList<>();
        for (User user : userList) {
                ids.add(user.getId());
        }
        HashSet<Integer> set = new HashSet<>(ids);
        ids.clear();
        ids.addAll(set);
        log.info("***********************"+ids.toString());

        if (MybatisUtil.condition(create_time) && !MybatisUtil.condition(totime)) {
            totime = LocalDateTime.now();
        }

        LambdaQueryWrapper<OrderStatus> OSqueryWrapper = new LambdaQueryWrapper<>();
        OSqueryWrapper.eq(MybatisUtil.condition(status), OrderStatus::getStatus, status)
                .between(MybatisUtil.condition(create_time), OrderStatus::getCreateTime, create_time, totime)
                .in(OrderStatus::getUserId, ids);


        List<OrderStatus> statusList = orderStatusService.list(OSqueryWrapper);
            if (statusList.isEmpty()){
                return R.error(CODE_400,"查询信息为空");
            }

        List<Integer> OrderIds = new ArrayList<>();
        for (OrderStatus orderStatus : statusList) {
            OrderIds.add(orderStatus.getOrderId());
        }
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.in(Order::getId, OrderIds)
                .last(MybatisUtil.limitPage(currentPage, pageSize));
        ;
        List<Order> OrderList = orderService.list(orderLambdaQueryWrapper);

        List<AllStudentInfo> allStudentInfos = allStudentInfoMapper.selectList(
                new LambdaQueryWrapper<AllStudentInfo>()
                .in(AllStudentInfo::getId, ids)
        );


        int total = OrderList.size(); //total

        List<OrderBaseDTO> orderBaseDTOList = new ArrayList<>();
        OrderBaseDTO orderBaseDTO = new OrderBaseDTO();

        for (Order order : OrderList) {
            BeanUtils.copyProperties(order, orderBaseDTO);
            orderBaseDTOList.add(orderBaseDTO);
        }

        for (OrderBaseDTO orderBaseDTO1 : orderBaseDTOList) {
            for (AllStudentInfo allStudentInfo : allStudentInfos) {
                if (orderBaseDTO1.getUserId() == allStudentInfo.getId()) {
                    orderBaseDTO1.setName(allStudentInfo.getName());
                    orderBaseDTO1.setCollege(allStudentInfo.getCollege());
                    orderBaseDTO1.setClassName(allStudentInfo.getClassName());
                    orderBaseDTO1.setMajor(allStudentInfo.getMajor());
                }
            }
        }

        return R.page(total, orderBaseDTOList);
    }

    @Transactional
    @Override
    public R softDeleteOne(Integer id) {
        try {
            LambdaUpdateWrapper<OrderStatus> updateWrapper = new LambdaUpdateWrapper<OrderStatus>();
            updateWrapper.eq(OrderStatus::getOrderId, id)
                    .set(OrderStatus::getStatus, 0);
            boolean result = orderStatusService.update(updateWrapper);
        } catch (Exception e) {
            throw new NormalException();
        }
        return R.success();
    }

    @Override
    public R softDeleteBatch(List<Integer> ids) {
        try {
            LambdaUpdateWrapper<OrderStatus> updateWrapper = new LambdaUpdateWrapper<OrderStatus>();
            updateWrapper.in(OrderStatus::getOrderId, ids)
                    .set(OrderStatus::getStatus, 0);
            orderStatusService.update(updateWrapper);
        } catch (Exception e) {
            throw new NormalException();
        }
        return R.success();
    }

    @Override
    @Transactional
    public R approvalOrder(Integer id, Integer agree) {
        LambdaUpdateWrapper<OrderStatus> updateWrapper = new LambdaUpdateWrapper<>();
        try {

            updateWrapper.eq(OrderStatus::getOrderId, id)
                    .set(OrderStatus::getStatus, agree);
            orderStatusService.update(updateWrapper);
        } catch (Exception e) {
            throw new NormalException();
        }
        return R.success();
    }
}







