package com.qxj.qingxiaojiamaster.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qxj.qingxiaojiamaster.utils.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/11 14:42
 **/


public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String token = request.getHeader("token");  //从请求头中获取token
        try {
            JWTUtils.verify(token);
            //验证token
            return  true;
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("msg","Token验证不通过，请登录后重试 ");
        }
        map.put("state",false);
        String json = new ObjectMapper().writeValueAsString(map); //将 map转为json
        response.setContentType("application/json;charset=UTF-8"); //设置json格式
        return  false;
    }
}