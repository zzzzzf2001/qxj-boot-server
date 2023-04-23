package com.qxj.qingxiaojiamaster.mapper;

import com.qxj.qingxiaojiamaster.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxj.qingxiaojiamaster.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
}
