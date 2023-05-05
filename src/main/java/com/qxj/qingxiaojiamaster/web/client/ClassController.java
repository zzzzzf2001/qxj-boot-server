package com.qxj.qingxiaojiamaster.web.client;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Class;
import com.qxj.qingxiaojiamaster.entity.Grade;
import com.qxj.qingxiaojiamaster.entity.Major;
import com.qxj.qingxiaojiamaster.service.ClassService;
import com.qxj.qingxiaojiamaster.service.CollegeService;
import com.qxj.qingxiaojiamaster.service.GradeService;
import com.qxj.qingxiaojiamaster.service.MajorService;
import com.qxj.qingxiaojiamaster.utils.MybatisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/4/24 10:23
 **/

@RestController
@RequestMapping("/class")
@Slf4j
public class ClassController {

    @Resource
    CollegeService collegeService;
    @Resource
    ClassService classService;
    @Resource
    GradeService gradeService;
    @Resource
    MajorService majorService;

    /**
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 获取全部学院
     * @author hasdsd
     * @Date 2023/4/24
     */
    @GetMapping("/college")
    public R getAllCollege() {
        return R.success(collegeService.list());
    }

    /**
     * @param collegeId
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 根据学院获取专业
     * @author hasdsd
     * @Date 2023/4/24
     */
    @GetMapping("/major")
    public R getMajorByCollegeId(@RequestParam("collegeId") Integer collegeId) {
        return R.success(majorService.lambdaQuery().eq(Major::getCollegeId, collegeId).list());
    }


    /**
     * @param collegeId
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 根据学院获取年级
     * @author hasdsd
     * @Date 2023/4/24
     */
    @GetMapping("/grade")
    public R getGradeByCollegeId(
            @RequestParam("collegeId") Integer collegeId
    ) {
        return R.success(gradeService.lambdaQuery().eq(MybatisUtil.condition(collegeId), Grade::getCollegeId, collegeId).list());
    }


    /**
     * @param majorId, classId, collegeId
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 查询班级信息
     * @author hasdsd
     * @Date 2023/4/24
     */
    @GetMapping("/list")
    public R getClassListByCollegeId(
            @RequestParam(value = "collegeId", required = false) Integer[] collegeId,
            @RequestParam(value = "majorId", required = false) Integer[] majorId,
            @RequestParam(value = "gradeId", required = false) Integer[] gradeId,
            @RequestParam(value = "classId", required = false) Integer[] classId
    ) {
        ArrayList<Integer> allMajors = new ArrayList<>();
        if (MybatisUtil.condition(majorId)) {
            allMajors.addAll(Arrays.asList(majorId));
        }
        //将collegeId转换为MajorId
        if (MybatisUtil.condition(collegeId)) {
            majorService.lambdaQuery().eq(Major::getCollegeId, collegeId).list().forEach(major -> allMajors.add(major.getId()));
        }
        List<Class> list = classService.lambdaQuery()
                .in(MybatisUtil.conditionArray(allMajors.toArray()), Class::getMajorId, allMajors)
                .in(MybatisUtil.conditionArray(gradeId), Class::getGradeId, gradeId)
                .in(MybatisUtil.conditionArray(classId), Class::getId, classId)
                .list();
        return R.success(list);
    }


    /**
     * @param majorId, classId, collegeId
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 查询班级信息
     * @author hasdsd
     * @Date 2023/4/24
     */
    @GetMapping()
    public R getClassByCollegeId(
            @RequestParam(value = "majorId", required = false) Integer majorId,
            @RequestParam(value = "gradeId", required = false) Integer gradeId
    ) {
        List<Class> list = classService.lambdaQuery().eq(Class::getMajorId, majorId)
                .eq(Class::getGradeId, gradeId).list();
        return R.success(list);
    }
}
