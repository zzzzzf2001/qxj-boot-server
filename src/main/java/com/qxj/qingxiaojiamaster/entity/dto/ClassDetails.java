package com.qxj.qingxiaojiamaster.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qxj.qingxiaojiamaster.entity.Class;
import lombok.*;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/13 14:15
 **/


@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ClassDetail")
public class ClassDetails extends Class {
    @TableField("gname")
    private String gname;

    @TableField("coname")
    private String coname;

    @TableField("mname")
    private String mname;
}
