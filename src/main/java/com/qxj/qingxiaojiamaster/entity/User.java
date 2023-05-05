package com.qxj.qingxiaojiamaster.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Getter
@Setter
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("number")
    private String number;

    @TableField("password")
    private String password;

    @TableField("crate_time")
    private LocalDateTime crateTime;

    @TableField("phone")
    private String phone;

    @TableField("emergency_phone")
    private String emergencyPhone;

    @TableField("class_id")
    private Integer classId;

    @TableField("status")
    private Integer status;

    @TableField("Remarks")
    private String remarks;



    /**
     *用户状态是否可用
     * 审批中：0
     * 正常可用：1
     * 已删除：2
     * 审批拒绝：-1
     *
     * */
    @TableField("enable")
    private Integer enable;


}
