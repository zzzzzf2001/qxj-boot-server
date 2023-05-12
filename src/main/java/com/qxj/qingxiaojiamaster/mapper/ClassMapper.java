package com.qxj.qingxiaojiamaster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxj.qingxiaojiamaster.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Mapper
public interface ClassMapper extends BaseMapper<Class> {

    List<Integer> getIdByAdminId(@Param("adminId") int adminId);

    List<Class> selectWithAdmin(@Param("majorId") Integer majorId, @Param("gradeId") Integer gradeId);
}
