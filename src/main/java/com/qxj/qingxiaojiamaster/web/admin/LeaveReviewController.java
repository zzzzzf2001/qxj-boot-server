package com.qxj.qingxiaojiamaster.web.admin;

import com.qxj.qingxiaojiamaster.common.R;
import com.qxj.qingxiaojiamaster.config.NormalException;
import com.qxj.qingxiaojiamaster.entity.Admin;
import com.qxj.qingxiaojiamaster.mapper.OrderMapper;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.qxj.qingxiaojiamaster.service.UserService;
import com.qxj.qingxiaojiamaster.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.qxj.qingxiaojiamaster.common.Constants.CODE_499;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/19 22:02
 * 请假审批管理
 **/

@Slf4j
@RestController
@RequestMapping("/admin/leave")
public class LeaveReviewController {


    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;

    @Resource
    private OrderMapper orderMapper;
    /*
       搜索请假信息      /select (条件查询,分页查询

        **/

    /**
     * @param admin,name,numer,enable,create_time,classId,currentPage,pageSize
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 查询请假信息
     * @author 15754
     * @Date 2023/5/5
     */
    @PostMapping("/select")
    public R selectLeave(
            @RequestBody Admin admin,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "number", required = false) String number,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "create_table", required = false) LocalDateTime create_time,
            @RequestParam(value = "to_time", required = false) LocalDateTime to_time,
            @RequestParam(value = "class_id", required = false) Integer classId,
            @RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletRequest request
    ) {
        String token = request.getHeader("token");
        if(Objects.isNull(token)){
            throw new NormalException(Integer.valueOf(CODE_499),"token过期或不可用");
        }
        String roleStr = JWTUtils.getToken(token).getClaim("role").asString();
        int role = Integer.parseInt(roleStr);
        admin.setRole(role);
        return orderService.getOrderPage(admin, name, number, status, create_time, to_time, classId, currentPage, pageSize);
    }


    /**
     * @param id 学生id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 删除学生请假信息
     * @author 15754
     * @Date 2023/5/5
     */
    @DeleteMapping("/delete")
    public R deleteOneOrder(@RequestParam("id") Integer id) {
        return orderService.softDeleteOne(id);
    }

    /**
     * @param ids
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 批量删除请假信息
     * @author 15754
     * @Date 2023/5/5
     */
    @PostMapping("/deleteBatch")
    public R deleteBatchByList(@RequestBody List<Integer> ids) {
        return orderService.softDeleteBatch(ids);
    }

    /**
     * @param id    订单id
     * @param agree 状态id
     * @return com.qxj.qingxiaojiamaster.common.R
     * @Description 审批假条
     * @author 15754
     * @Date 2023/5/5
     */
    @PostMapping("/approval/{agree}")
    public R approvalOrder(@RequestParam("id") Integer id, @PathVariable("agree") Integer agree) {
        /** agree取值
         审核通过      2
         审核未通过    3
         销假未通过    5
         已销假       7
         */
        return orderService.approvalOrder(id, agree);
    }


}
