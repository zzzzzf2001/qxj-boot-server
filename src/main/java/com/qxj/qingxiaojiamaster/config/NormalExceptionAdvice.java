package com.qxj.qingxiaojiamaster.config;

import com.qxj.qingxiaojiamaster.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/4/22 09:32
 **/

@ControllerAdvice
@Slf4j
public class NormalExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(NormalException.class)
    public R handleBusinessException(NormalException e) {
        log.error("错误:" + e.getDebugInfo());
        return R.error(String.valueOf(e.getCode()), "异常：" + e.getMessage());
    }
}
