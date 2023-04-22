package com.qxj.qingxiaojiamaster.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

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
@TableName("admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("number")
    private String number;

    @TableField("name")
    private String name;

    @TableField("password")
    private String password;

    @TableField("college_id")
    private Integer collegeId;

    @TableField("role")
    private Integer role;


}
