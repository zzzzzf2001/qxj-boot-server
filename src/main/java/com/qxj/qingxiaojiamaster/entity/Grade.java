package com.qxj.qingxiaojiamaster.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


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
@TableName("grade")
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("college_id")
    private Integer collegeId;

    @TableField("major_id")
    private Integer majorId;
}
