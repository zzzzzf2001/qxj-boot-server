package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private ClassService classService;
    @Resource
    private ClassMapper classMapper;
    @Resource
    private UserService userService;




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

    @Override
    public List<User> getRegistryUser( Admin admin, String name, String number, Integer enable, LocalDateTime create_time, Integer classId, Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<Class> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getAdminId,admin.getId());
        List<Class> classes = classMapper.selectList(queryWrapper);
        ArrayList<Integer> classIds=new ArrayList<>();
        for (Class cl:classes){
            classIds.add(cl.getId());
        }
        log.info(classes.toString());
        LambdaQueryWrapper<User> userQueryWrapper=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> wrapper = userQueryWrapper.eq(MybatisUtil.condition(name), User::getName, name)
                .eq(MybatisUtil.condition(number),User::getNumber,number)
                .eq(MybatisUtil.condition(enable), User::getEnable, enable)
                .eq(MybatisUtil.condition(create_time), User::getCrateTime, create_time)
                .eq(MybatisUtil.condition(classId), User::getClassId, classId)
                .in(!MybatisUtil.condition(classId), User::getClassId, classIds)
                .last(MybatisUtil.condition(currentPage)&&MybatisUtil.condition(pageSize),MybatisUtil.limitPage(currentPage, pageSize));
            log.info(wrapper.toString());
        List<User> list = userService.list(wrapper);
        return list;
    }
}
