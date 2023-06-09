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
@TableName("class")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("grade_id")
    private Integer gradeId;

    @TableField("major_id")
    private Integer majorId;


    @TableField("admin_id")
    private Integer adminId;

    //规范？什么规范？
    @TableField(value = "admin_name", exist = false)
    private String adminName;
}
