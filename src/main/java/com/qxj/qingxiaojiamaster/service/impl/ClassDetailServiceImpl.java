package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxj.qingxiaojiamaster.entity.dto.ClassDetails;
import com.qxj.qingxiaojiamaster.mapper.ClassDetailMapper;
import com.qxj.qingxiaojiamaster.service.ClassDetailService;
import org.springframework.stereotype.Service;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/13 15:32
 **/

@Service
public class ClassDetailServiceImpl  extends ServiceImpl<ClassDetailMapper, ClassDetails> implements ClassDetailService {
}
