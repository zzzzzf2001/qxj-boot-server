package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.model.PageParams;

import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.entity.dto.UserDetails;
import com.qxj.qingxiaojiamaster.mapper.ClassMapper;
import com.qxj.qingxiaojiamaster.mapper.OrderMapper;
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


    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderMapper orderMapper;


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

    /**
     * @param admin,user,number,enable,create_time,classId,currentPage,pageSize
     * @return com.qxj.qingxiaojiamaster.entity.User
     * @Description 用户注册获取信息
     * @author 15754
     * @Date 2023/4/24
     */
    @Override

    public R getRegistryUser(Admin admin, String name, String number, Integer enable, LocalDateTime create_time, LocalDateTime to_time, Integer classId, Integer currentPage, Integer pageSize) {

        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();

        ArrayList<Class> classes = new ArrayList<>();
        //首先要将管理员ID放入条件查询器
        //判断用户权限，若是是超级管理员

        if(admin.getRole()==1) {
            queryWrapper.ge(Class::getId, 0);
            List<Class> classList = classMapper.selectList(queryWrapper);
            classes.addAll(classList);
        }
        //若是普通管理员
        else {
            queryWrapper.eq(Class::getAdminId, admin.getId());
            //查询出该管理员所管辖的班级
            List<Class> classList = classMapper.selectList(queryWrapper);
            classes.addAll(classList);
        }

        if (classes.size()==0){
            throw new NormalException("查询不到该管理员的注册用户信息");
        }


        //将管辖班级的ID全部取出
        ArrayList<Integer> classIds = new ArrayList<>();
        for (Class cl : classes) {
            classIds.add(cl.getId());
        }




        if (MybatisUtil.condition(create_time) && !MybatisUtil.condition(to_time)) {
            to_time = LocalDateTime.now();
        }


        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<User>()
                //模糊查询姓名
                .like(StringUtils.isNotEmpty(name), User::getName, name)
                //查询学号
                .eq(StringUtils.isNotEmpty(number), User::getNumber, number)
                //查询是否可用
                .eq(MybatisUtil.condition(enable), User::getEnable, enable)
                //若指定班级号，根据班级号查询
                .eq(MybatisUtil.condition(classId), User::getClassId, classId)
                //指定用户创建时间的所在区间
                .between(MybatisUtil.condition(create_time) , User::getCrateTime, create_time, to_time)
                //若未指定则直接按照该老师所管辖的班级ID搜索
                .in(!MybatisUtil.condition(classId), User::getClassId, classIds)
                //按照创建时间排序
                .orderBy(MybatisUtil.condition(create_time), false, User::getCrateTime);

        Page<User> page = new Page<>();

        if (MybatisUtil.condition(currentPage)&&MybatisUtil.condition(pageSize)){
            page.setCurrent(currentPage);
            page.setPages(pageSize);
        }

        Page<User> userPage = userMapper.selectPage(page, userQueryWrapper);

        List<User> records = userPage.getRecords();

        long total = userPage.getTotal();

        return R.page(total,records);
    }



    @Override
    public UserDetails getUserDetail(Integer userId) {
        UserDetails detail = userMapper.getDetailById(userId);
        String picurl = orderMapper.selectPicByUID(userId);
        detail.setOrderPicUrl(picurl);
        return detail;
    }


}