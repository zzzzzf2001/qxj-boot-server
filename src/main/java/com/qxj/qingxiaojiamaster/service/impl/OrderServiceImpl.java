package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxj.qingxiaojiamaster.common.PageParams;
import com.qxj.qingxiaojiamaster.common.R;
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


    //列出所有假条
    @Override
    public List<Order> selectAllOrderInfo(PageParams pageParams,int id) {
        //创建Map 将查询条件插入其中
        Map<String,Object> map=new HashMap<>();
        map.put("userId",id); //用户ID
        map.put("pageNo",pageParams.getPageNo()); //页号
        map.put("pageNum",pageParams.getPageSize()); //页大小
        return orderMapper.selectOrderByUserID(map);
    }




    @Override
    public R selectOrderByStatus(PageParams pageParams, int status) {
        //通过请假单状态找到所有请假表的ID
        List<Integer> orderids = orderStatusMapper.selectOrderidByStatus(status);
        //创建条件查询器
        LambdaQueryWrapper<Order> queryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件 倘若id为空，则会直接查询 ，不为空就按条件查询 orderid in (orderids)
        queryWrapper.in(!orderids.isEmpty(),Order::getId,orderids);
        //分页查询，创建分页类
        Page<Order> page = new Page<Order>(pageParams.getPageNo(),pageParams.getPageSize());
        //分页查询返回页结果
        Page<Order> orderPage1 = orderMapper.selectPage(page, queryWrapper);
        return R.success(orderPage1);
    }

}
