package com.qxj.qingxiaojiamaster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.entity.dto.UserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

   UserDetails getDetailById(@Param("userId") Integer id);

    Map<String, String> selectOtherUserInfo(@Param("classId") Integer id);
}
