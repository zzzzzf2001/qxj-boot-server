package com.qxj.qingxiaojiamaster.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

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

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
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


}
