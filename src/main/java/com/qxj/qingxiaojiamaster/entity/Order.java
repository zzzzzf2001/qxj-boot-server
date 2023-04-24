package com.qxj.qingxiaojiamaster.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@TableName("`order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("reason")
    private String reason;

    @TableField("reason_type")
    private Integer reasonType;

    @TableField("from_time")
    private LocalDateTime fromTime;

    @TableField("to_time")
    private LocalDateTime toTime;

    @TableField("healthy")
    private String healthy;

    @TableField("to_area")
    private String toArea;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField("img_url")
    private String imgUrl;


}
