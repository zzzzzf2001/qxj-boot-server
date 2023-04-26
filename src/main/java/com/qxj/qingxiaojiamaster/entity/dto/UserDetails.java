package com.qxj.qingxiaojiamaster.entity.dto;

import com.qxj.qingxiaojiamaster.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/26 19:34
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetails extends User {
    private String college;
    private String major;
    private String className;
    private String orderPicUrl;
}
