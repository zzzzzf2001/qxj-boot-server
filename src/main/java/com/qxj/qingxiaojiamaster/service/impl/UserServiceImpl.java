package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.common.PageParams;
import com.qxj.qingxiaojiamaster.common.R;
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

import static com.qxj.qingxiaojiamaster.common.Constants.CODE_400;

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
    private PageParams pageParams;



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
     * @Description 用户登录获取信息
     * @author 15754
     * @Date 2023/4/24
     */
    @Override
    public R getRegistryUser( Admin admin, String name, String number, Integer enable, LocalDateTime create_time, Integer classId, Integer currentPage, Integer pageSize) {

        LambdaQueryWrapper<Class> queryWrapper=new LambdaQueryWrapper<>();
        //首先要将管理员ID放入条件查询器中
        queryWrapper.eq(Class::getAdminId,admin.getId());
        //查询出该管理员所管辖的班级
        List<Class> classes = classMapper.selectList(queryWrapper);
        //将管辖班级的ID全部取出
        ArrayList<Integer> classIds=new ArrayList<>();
        for (Class cl:classes){
            classIds.add(cl.getId());
        }
        //判断curreage与pagesize是否为空（避免空指针异常），倘若有一人空则会直接走默认值
         if (MybatisUtil.condition(currentPage)&&MybatisUtil.condition(pageSize)){
             //二者皆为非空才可以设置值
             pageParams.setPageSize(pageSize);
             pageParams.setCurrentPage(currentPage);
         }


        List<User> list = userService.list(
                new LambdaQueryWrapper<User>()
                        //模糊查询姓名
                        .like(MybatisUtil.condition(name), User::getName, name)
                        //查询学号
                        .eq(MybatisUtil.condition(number),User::getNumber,number)
                        //查询是否可用
                        .eq(MybatisUtil.condition(enable), User::getEnable, enable)
                        //查询创建时间
                        .eq(MybatisUtil.condition(create_time), User::getCrateTime, create_time)
                        //若指定班级号，根据班级号查询
                        .eq(MybatisUtil.condition(classId), User::getClassId, classId)
                        //若未指定则直接按照该老师所管辖的班级ID搜索
                        .in(!MybatisUtil.condition(classId), User::getClassId, classIds)
                        //按照创建时间排序
                        .orderBy(MybatisUtil.condition(create_time), false,User::getCrateTime)
                        //分页查询默认第一页，一页10个
                        .last(MybatisUtil.limitPage(pageParams.getCurrentPage(),pageParams.getPageSize())
        ));
        return list.size()>0? R.success(list): R.error(CODE_400,"暂未查询到您所管理的学生信息");
    }
}
