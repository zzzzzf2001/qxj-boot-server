package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.mapper.ClassMapper;
import com.qxj.qingxiaojiamaster.mapper.UserMapper;
import com.qxj.qingxiaojiamaster.service.ClassService;
import com.qxj.qingxiaojiamaster.service.UserService;
import com.qxj.qingxiaojiamaster.utils.LoginUtil;
import com.qxj.qingxiaojiamaster.utils.MybatisUtil;
import org.springframework.stereotype.Service;
import com.qxj.qingxiaojiamaster.entity.Class;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private ClassService classService;
    @Resource
    private ClassMapper classMapper;



    @Override
    public List<User> getRegistryUser(Admin admin, String name, String number, int enable,
                                      LocalDateTime create_time, String college, String major, String className,
                                      int currentPage,int pageSize
                                                ) {
        LambdaQueryWrapper<Class> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getAdminId,admin.getId());
        List<Class> classes = classMapper.selectList(queryWrapper);
        ArrayList<Integer> classIds=new ArrayList<>();
        for (Class cl:classes){
            classIds.add(cl.getId());
        }

        LambdaQueryWrapper<User> userQueryWrapper=new LambdaQueryWrapper<>();
        userQueryWrapper.eq(MybatisUtil.condition(name),User::getName,name)
                        .eq(MybatisUtil.condition(enable),User::getStatus,enable)
                        .eq(MybatisUtil.condition(create_time),User::getCrateTime,create_time)
                        ;







        return null;
    }


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
        if (result.getEnable() != 1) {
            throw new NormalException("账号不可用");
        }
        LoginUtil.checkAccount(user.getPassword(), result.getPassword());
        result.setPassword("");
        return result;
    }
}
