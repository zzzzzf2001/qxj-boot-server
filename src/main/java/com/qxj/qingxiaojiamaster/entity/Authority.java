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
@TableName("authority")
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("class_id")
    private Integer classId;

    @TableField("admin_id")
    private Integer adminId;


}
