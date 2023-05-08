package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.mapper.AdminMapper;
import com.qxj.qingxiaojiamaster.service.AdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Admin Login(Admin admin) {
        Admin result = lambdaQuery()
                .eq(Admin::getNumber, admin.getNumber())
                .one();

        if (result == null) {
            throw new NormalException("账号不存在");
        }
        if (result.getEnable() != 1) {
            throw new NormalException("账号不可用");
        }
        LoginUtil.checkAccount(admin.getPassword(), result.getPassword());
        return result;
    }
}
