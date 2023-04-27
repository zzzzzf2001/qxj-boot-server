package com.qxj.qingxiaojiamaster.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

import java.time.LocalDateTime;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/27 08:43
 **/


@TableName("all_student_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class studentInfo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("number")
    private String number;


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
     * 审批拒绝：-1
     *
     * */
    @TableField("enable")
    private Integer enable;


    @TableField("grade")
    private int grade;


    @TableField("class")
    private String className;

    @TableField("major")
    private String major;

    @TableField("college")
    private String college;

}
