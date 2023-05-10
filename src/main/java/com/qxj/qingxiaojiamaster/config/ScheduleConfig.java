package com.qxj.qingxiaojiamaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/10 21:52
 **/


@Configuration
public class ScheduleConfig {
    /**
     * 修复同一时间无法执行多个定时任务问题。@Scheduled默认是单线程的
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量。
        taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        return taskScheduler;
    }
}