package com.qxj.qingxiaojiamaster.utils;

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
        return String.format("limit %s,%s", currentPage * pageSize - 1, pageSize);
    }
}
