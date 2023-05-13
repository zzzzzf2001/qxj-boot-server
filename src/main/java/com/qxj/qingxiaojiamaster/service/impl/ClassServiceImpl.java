package com.qxj.qingxiaojiamaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qxj.qingxiaojiamaster.entity.Class;
import com.qxj.qingxiaojiamaster.entity.College;
import com.qxj.qingxiaojiamaster.entity.Grade;
import com.qxj.qingxiaojiamaster.entity.Major;
import com.qxj.qingxiaojiamaster.entity.dto.ClassDetails;
import com.qxj.qingxiaojiamaster.mapper.ClassMapper;
import com.qxj.qingxiaojiamaster.mapper.CollegeMapper;
import com.qxj.qingxiaojiamaster.mapper.GradeMapper;
import com.qxj.qingxiaojiamaster.mapper.MajorMapper;
import com.qxj.qingxiaojiamaster.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Resource
    ClassMapper classMapper;
    @Resource
    MajorMapper majorMapper;
    @Resource
    GradeMapper gradeMapper;
    @Resource
    CollegeMapper collegeMapper;


    @Override
    public List<ClassDetails> getClassDetails(Integer adminId) {


        classMapper.selectAll(adminId);

//        List<ClassDetails> classDetailsList=new ArrayList<>();
//        List<Class> classes = classMapper.selectclassByAdminId(adminId);
//        if (classes==null){
//            return null;
//        }
//
//        for (Class c:classes){
//            ClassDetails classDetails = new ClassDetails();
//            BeanUtils.copyProperties(c,classDetails);
//        }
//        List<Integer> majorIds = classes.stream().map(Class::getMajorId).collect(Collectors.toList());
//
//        List<Integer> GradeIds= classes.stream().map(Class::getGradeId).collect(Collectors.toList());
//
//        List<Major> majors = majorMapper.selectList(new LambdaQueryWrapper<Major>()
//                .in(Major::getId, majorIds));
//        List<Grade> grade = gradeMapper.selectList(new LambdaQueryWrapper<Grade>()
//                .in(Grade::getId, GradeIds));
//        List<Integer> collegeIds = majors.stream().map(Major::getCollegeId).collect(Collectors.toList());
//
//        List<College> colleges = collegeMapper.selectList(new LambdaQueryWrapper<College>()
//                .in(College::getId, collegeIds));
        return null;
    }
}
