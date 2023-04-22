package com.qxj.qingxiaojiamaster.utils;

import com.qxj.qingxiaojiamaster.config.NormalException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/4/22 13:41
 **/

@Slf4j
public class LoginUtil {


    /**
     * @param checkValue,realValue
     * @return void
     * @Description 检查密码是否正确
     * @author hasdsd
     * @Date 2023/4/22
     */
    public static void checkAccount(String checkValue, String realValue) {
        if (!checkValue.equals(realValue)) {
            throw new NormalException("账户名或密码错误");
        }

    }


}
