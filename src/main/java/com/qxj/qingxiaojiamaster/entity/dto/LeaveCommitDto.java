package com.qxj.qingxiaojiamaster.entity.dto;

import com.qxj.qingxiaojiamaster.entity.Order;
import com.qxj.qingxiaojiamaster.entity.User;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/22 11:22
 *
 * 请假提交返回类
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveCommitDto  extends Order {
    private String number; //学生学号
    private String picurl;//学生图片URL
    private String college;//学院名称
    private String major;//专业
    private String className; //班级
    private String phone; //学生电话号
    private String emergencyPhone; //学生紧急电话
    private int status; //假条状态
}
