package com.qxj.qingxiaojiamaster.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/11 14:39
 **/


public class JWTUtils {

    /**
     * 生成token,header,payload,signature
     * */
    private static  final String SIGN="@12#$3ASD#%!@";

    public static String getToken(HashMap<String, String> map){
        //设置默认时间
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.MINUTE,5);
        //创建JWT实例
        JWTCreator.Builder builder = JWT.create();
        //将Payload传入JWT实例中
        map.forEach(builder::withClaim);
        //设置过期时间

        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SIGN));
    }
    /**
     * 验证token
     * */
    public static void verify(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    /**
     * 获取token中的信息
     * */
    public static DecodedJWT  getToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }
}