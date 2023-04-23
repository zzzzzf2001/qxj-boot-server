package com.qxj.qingxiaojiamaster.mapper;

import com.qxj.qingxiaojiamaster.entity.OrderStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface OrderStatusMapper extends BaseMapper<OrderStatus> {

    boolean setStatusNew(int id,int status);

    OrderStatus getStatusByorderID(int id);

    List<Integer> selectOrderidByStatus(int status);
}
