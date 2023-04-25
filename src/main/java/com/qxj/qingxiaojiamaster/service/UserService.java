package com.qxj.qingxiaojiamaster.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.User;

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

    List<User> getRegistryUser(Admin admin, String name, String number, Integer enable, LocalDateTime create_time, Integer classId, Integer currentPage, Integer pageSize);

}
