package com.qxj.qingxiaojiamaster.config;

import com.qxj.qingxiaojiamaster.mapper.OrderStatusMapper;
import com.qxj.qingxiaojiamaster.service.OrderService;
import com.qxj.qingxiaojiamaster.service.OrderStatusService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/10 20:34
 **/

@Configuration
@EnableScheduling
public class StaticScheduleTask {

    @Resource
    OrderService orderService;
    @Resource
    OrderStatusService orderStatusService;


    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void checkHasExpire(){

        try {
            if(orderService.hasExpire().size()==0) {
                return;
            }
            List<Integer> orderIds = orderService.hasExpire();

            boolean result = orderStatusService.expireOrder(orderIds);

            System.out.println("已将过期假条改正状态");
        }
        catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }


    }


}
