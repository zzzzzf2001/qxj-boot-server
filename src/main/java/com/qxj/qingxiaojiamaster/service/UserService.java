package com.qxj.qingxiaojiamaster.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.entity.dto.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
public interface UserService extends IService<User> {


    User Login(User user);

    R getRegistryUser(Admin admin, String name, String number, Integer enable, LocalDateTime create_time, Integer classId, Integer currentPage, Integer pageSize);

    UserDetails getUserDetail(Integer userId);
}
