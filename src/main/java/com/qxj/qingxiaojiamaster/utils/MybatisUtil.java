package com.qxj.qingxiaojiamaster.utils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/4/23 14:10
 **/


public class MybatisUtil {

    /**
     * @param currentPage, pageSize
     * @return java.lang.String
     * @Description 分页
     * @author hasdsd
     * @Date 2023/4/23
     */
    public static String limitPage(Integer currentPage, Integer pageSize) {
        return String.format("limit %s,%s", (currentPage - 1) * pageSize, pageSize);
    }

    /**
     * @param array 输入的数组
     * @return java.lang.String
     * @Description 处理数组
     * @author hasdsd
     * @Date 2023/4/23
     */
    @Deprecated
    public static String handleArray(String[] array) {
        //除去两个中括号
        return Arrays.toString(array).replace('[', ' ').replace(']', ' ');
    }


    /**
     * 条件构造是否过滤空值
     *
     * @param object 判断的值
     * @return mp的条件构造器用
     */
    public static boolean condition(Object object) {
        return !Objects.isNull(object);
    }
}
