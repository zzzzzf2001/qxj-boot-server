package com.qxj.qingxiaojiamaster.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author hasdsd
 * @since 2023-04-27
 */
@Getter
@Setter
@TableName("all_student_info")
public class AllStudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String number;

    /**
     * 用户状态 (-1注册审核驳回,0审核驳回，1待审核,2审核通过)
     */
    private Integer status;

    private String phone;

    private String emergencyPhone;

    private Integer enable;

    private LocalDateTime createTime;

    private String grade;

    @TableField("class")
    private String className;

    private String major;

    private String college;


}
