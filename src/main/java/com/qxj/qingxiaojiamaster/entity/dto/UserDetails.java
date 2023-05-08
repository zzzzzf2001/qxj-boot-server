package com.qxj.qingxiaojiamaster.entity.dto;

import com.qxj.qingxiaojiamaster.entity.User;
import lombok.*;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/26 19:34
 **/


@EqualsAndHashCode(callSuper = true)
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
