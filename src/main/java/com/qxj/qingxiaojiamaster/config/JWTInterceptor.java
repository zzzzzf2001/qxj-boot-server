package com.qxj.qingxiaojiamaster.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qxj.qingxiaojiamaster.utils.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static com.qxj.qingxiaojiamaster.common.Constants.CODE_499;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/11 14:42
 **/


public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");  //从请求头中获取token
        if (request.getMethod().equalsIgnoreCase("options")) {
            return true;
        }
        if(token!=null) {
                JWTUtils.verify(token,response);
                //验证token
                return true;
        }

        else {
            throw new NormalException(Integer.parseInt(CODE_499),"无token请重新登陆");
        }

        //放行options


    }
}
