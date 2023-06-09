package com.qxj.qingxiaojiamaster.web.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Class;
import com.qxj.qingxiaojiamaster.entity.College;
import com.qxj.qingxiaojiamaster.entity.Grade;
import com.qxj.qingxiaojiamaster.entity.Major;
import com.qxj.qingxiaojiamaster.entity.dto.ClassDetails;
import com.qxj.qingxiaojiamaster.mapper.ClassDetailMapper;
import com.qxj.qingxiaojiamaster.service.*;
import com.qxj.qingxiaojiamaster.utils.JWTUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.qxj.qingxiaojiamaster.common.Constants.CODE_499;

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

    @Resource
    ClassDetailMapper classDetailMapper;


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
     * @param name, id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 更新学院
     * @author hasdsd
     * @Date 2023/5/9
     */
    @PutMapping("/college")
    public R updatesCollege(
            @RequestParam("name") String name,
            @RequestParam("id") Integer id
    ) {
        return R.success(collegeService.updateById(new College().builder().name(name).id(id).build()));
    }

    /**
     * @param id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 删除学院
     * @author hasdsd
     * @Date 2023/5/9
     */
    @DeleteMapping("/college")
    public R deleteCollege(
            @RequestParam("id") Integer id
    ) {
        return R.success(collegeService.removeById(new College().builder().id(id).build()));
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
     * @param id, name
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 更新专业
     * @author hasdsd
     * @Date 2023/5/9
     */
    @PutMapping("/major")
    public R updateMajor(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name
    ) {
        return R.success(majorService.updateById(new Major().builder().name(name).id(id).build()));
    }

    /**
     * @param id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 删除专业
     * @author hasdsd
     * @Date 2023/5/9
     */
    @DeleteMapping("/major")
    public R deleteMajor(
            @RequestParam("id") Integer id
    ) {
        return R.success(majorService.removeById(new Major().builder().collegeId(id).build()));
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
     * @param id, name
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 更新年级
     * @author hasdsd
     * @Date 2023/5/9
     */
    @PutMapping("/grade")
    public R upgradeGrade(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name
    ) {
        return R.success(gradeService.updateById(new Grade().builder().id(id).name(name).build()));
    }


    /**
     * @param id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 删除年级
     * @author hasdsd
     * @Date 2023/5/9
     */
    @DeleteMapping("/grade")
    public R deleteGrade(
            @RequestParam("id") Integer id
    ) {
        return R.success(gradeService.removeById(new Grade().builder().id(id).build()));
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
                .adminId(adminId).majorId(majorId).gradeId(gradeId).name(name).build()));
    }

    /**
     * @param id, name
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 更新班级
     * @author hasdsd
     * @Date 2023/5/9
     */
    @PutMapping("/class")
    public R putClass(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("adminId") Integer adminId
    ) {
        return R.success(classService.updateById(new Class().builder()
                .id(id).name(name).adminId(adminId).build()));
    }

    /***
     * @param id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 删除班级
     * @author hasdsd
     * @Date 2023/5/9
     */
    @DeleteMapping("/class")
    public R deleteClass(
            @RequestParam("id") Integer id
    ) {
        return R.success(classService.removeById(new Class().builder()
                .id(id).build()));
    }

    @GetMapping("/showClassDetail")
    public R showClassDetail(HttpServletRequest request){
        String token = request.getHeader("token");
        if (token==null){
            throw new NormalException(Integer.valueOf(CODE_499),"token为空需重新登录");
        }
        Integer adminId = JWTUtils.getTokenDetail(token, "id", Integer.class);

        List<ClassDetails> classDetailsList = classDetailMapper.selectList
                (new LambdaQueryWrapper<ClassDetails>()
                .eq(ClassDetails::getAdminId, adminId));

        return R.success(classDetailsList);
    }



}
