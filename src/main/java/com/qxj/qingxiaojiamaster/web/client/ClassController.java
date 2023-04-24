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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/4/24 10:23
 **/

@RestController
@RequestMapping("/menu")
public class ClassController {

    @Resource
    CollegeService collegeService;
    @Resource
    ClassService classService;
    @Resource
    GradeService gradeService;
    @Resource
    MajorService majorService;
    private List<Integer> majorIds;

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
     * @Description 根据专业获取年级
     * @author hasdsd
     * @Date 2023/4/24
     */
    @GetMapping("/grade")
    public R getGradeByCollegeId(
            @RequestParam("collegeId") Integer collegeId
    ) {
        gradeService.lambdaQuery().eq(MybatisUtil.condition(collegeId), Grade::getCollegeId, collegeId);
        return R.success();
    }


    /**
     * @param majorId, classId, collegeId
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 查询班级
     * @author hasdsd
     * @Date 2023/4/24
     */
    @GetMapping("/class")
    public R getClassByCollegeId(
            @RequestParam("majorId") Integer[] majorId,
            @RequestParam("classId") Integer[] classId,
            @RequestParam("collegeId") Integer collegeId
    ) {
        //将collegeId转换为MajorId
        if (MybatisUtil.condition(collegeId)) {
            majorIds = Arrays.asList(majorId);
            List<Major> list = majorService.lambdaQuery().eq(Major::getCollegeId, collegeId).list();
            for (Major major : list) {
                majorIds.add(major.getId());
            }
        }
        List<Class> list = classService.lambdaQuery()
                .in(Class::getMajorId, majorIds)
                .in(Class::getClass)
                .list();
        return R.success(list);
    }
}
