package com.qxj.qingxiaojiamaster.entity.dto;

import com.qxj.qingxiaojiamaster.entity.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/5 14:55
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderBaseDTO extends Order {
    private String major;
    private String college;
    private String className;
    private String name;
    private Integer status;
}
