package com.qxj.qingxiaojiamaster.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_status")
public class OrderStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("order_id")
    private Integer orderId;

    @TableField("status")
    /**
     * 审核中        1
     * 审核通过      2
     * 审核未通过    3
     * 销假中       4
     * 销假未通过    5
     * 销假审核超时  6
     * 已销假       7
     * 审核超时     8
     * 已取消       9
     **/
    private Integer status;


    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("user_id")
    private Integer userId;


}
