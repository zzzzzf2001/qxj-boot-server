package com.qxj.qingxiaojiamaster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.entity.dto.ClassDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/13 15:30
 **/

@Mapper
public interface ClassDetailMapper extends BaseMapper<ClassDetails> {
}
