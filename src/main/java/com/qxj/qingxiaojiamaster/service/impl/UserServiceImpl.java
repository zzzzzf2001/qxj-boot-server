package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.mapper.UserMapper;
import com.qxj.qingxiaojiamaster.service.UserService;
import com.qxj.qingxiaojiamaster.utils.LoginUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * @param user
     * @return com.qxj.qingxiaojiamaster.entity.User
     * @Description 用户登录获取信息
     * @author hasdsd
     * @Date 2023/4/22
     */
    public User Login(User user) {
        //this指的是userService
        User result = this.lambdaQuery().eq(User::getNumber, user.getNumber()).one();
        if (result == null) {
            throw new NormalException("账号不存在");
        }
        //检测是否正确
        LoginUtil.checkAccount(user.getPassword(), result.getPassword());
        result.setPassword("");
        return result;
    }
}
