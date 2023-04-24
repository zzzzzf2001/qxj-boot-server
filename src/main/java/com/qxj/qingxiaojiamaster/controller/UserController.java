package com.qxj.qingxiaojiamaster.controller;


import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.entity.User;
import com.qxj.qingxiaojiamaster.mapper.UserMapper;
import com.qxj.qingxiaojiamaster.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 张锋
 * @since 2023-04-22
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;

    /**
     * @param user
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description checkCheckQuestion
     * @author hasdsd
     * @Date 2023/4/22
     */
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        //获取用户信息
        User userInfo = userService.Login(user);
        //面对困难的最好办法是逃避它
        Map<String, String> otherUserInfo = userMapper.selectOtherUserInfo(userInfo.getId());
        
        //放到一块
        HashMap<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);
        map.put("details", otherUserInfo);

        //不会用拉姆达
        //Class userClass = classService.lambdaQuery().eq(Class::getId, userInfo.getClassId()).one();
        //Grade grade = gradeService.lambdaQuery().eq(Grade::getId, userClass.getGradeId()).one();
        //Major major = majorService.lambdaQuery().eq(Major::getId, userClass.getMajorId()).one();
        //College college = collegeService.lambdaQuery().eq(College::getId, major.getCollegeId()).one();

        return R.success(map);
    }
}
