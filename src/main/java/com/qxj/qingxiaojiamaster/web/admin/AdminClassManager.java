package com.qxj.qingxiaojiamaster.web.admin;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.Class;
import com.qxj.qingxiaojiamaster.entity.College;
import com.qxj.qingxiaojiamaster.entity.Grade;
import com.qxj.qingxiaojiamaster.entity.Major;
import com.qxj.qingxiaojiamaster.service.ClassService;
import com.qxj.qingxiaojiamaster.service.CollegeService;
import com.qxj.qingxiaojiamaster.service.GradeService;
import com.qxj.qingxiaojiamaster.service.MajorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/5/9 00:06
 **/

@RestController
@RequestMapping("/class")
public class AdminClassManager {
    @Resource
    CollegeService collegeService;
    @Resource
    MajorService majorService;
    @Resource
    GradeService gradeService;
    @Resource
    ClassService classService;

    /**
     * @param collegeName
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 新增学院
     * @author hasdsd
     * @Date 2023/5/9
     */
    @PostMapping("/college")
    public R addCollege(@RequestParam("collegeName") String collegeName) {
        return R.success(collegeService.save(new College().builder().name(collegeName).build()));
    }

    /**
     * @param collegeId, name
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 新增专业
     * @author hasdsd
     * @Date 2023/5/9
     */
    @PostMapping("/major")
    public R addMajor(
            @RequestParam("collegeId") Integer collegeId,
            @RequestParam("name") String name
    ) {
        return R.success(majorService.save(new Major().builder().name(name).collegeId(collegeId).build()));
    }

    /**
     * @param collegeId, majorId, name
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 新增年级
     * @author hasdsd
     * @Date 2023/5/9
     */
    @PostMapping("/grade")
    public R addGrade(
            @RequestParam("collegeId") Integer collegeId,
            @RequestParam("majorId") Integer majorId,
            @RequestParam("name") String name
    ) {
        return R.success(gradeService.save(new Grade().builder().collegeId(collegeId).majorId(majorId).name(name).build()));
    }

    /**
     * @param gradeId, majorId, adminId, name
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 新增班级
     * @author hasdsd
     * @Date 2023/5/9
     */
    @PostMapping("/class")
    public R addClass(
            @RequestParam("gradeId") Integer gradeId,
            @RequestParam("majorId") Integer majorId,
            @RequestParam("adminId") Integer adminId,
            @RequestParam("name") String name
    ) {
        return R.success(classService.save(new Class().builder()
                .adminId(adminId).majorId(majorId).majorId(majorId).name(name).build()));
    }
}
