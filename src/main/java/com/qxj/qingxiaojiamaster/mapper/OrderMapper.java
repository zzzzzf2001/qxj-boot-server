package com.qxj.qingxiaojiamaster.mapper;

import com.qxj.qingxiaojiamaster.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxj.qingxiaojiamaster.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> selectOrderByUserID(int id);



}
