package com.qxj.qingxiaojiamaster.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;


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
@TableName("admin")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("number")
    private String number;

    @TableField("name")
    private String name;

    @TableField("password")
    private String password;

    @TableField("college")
    private String college;
    /**
     * 0:普通管理员
     * 1:超级管理员
     */

    @TableField("role")
    private Integer role;

    @TableField("enable")
    private Integer enable;


}
