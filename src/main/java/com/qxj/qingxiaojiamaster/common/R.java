package com.qxj.qingxiaojiamaster.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/4/22 09:29
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {
    private String code = "200";//默认值
    private String msg;
    private Object data;

    private Long total;

    //分页构造函数
    public R(String code, Long total, Object data) {
        this.code = code;
        this.data = data;
        this.total = total;
    }

    public R(String code, String msg, Object o) {
        this.code = code;
        this.msg = msg;
        this.data = o;
    }

    // 不需要返回值
    public static R success() {
        return new R(Constants.CODE_200, "操作成功", null);
    }

    // 需要返回值
    public static R success(Object data) {
        return new R(Constants.CODE_200, "", data);
    }

    public static R success(String msg, Object data) {
        return new R(Constants.CODE_200, msg, data);
    }

    //多个返回值
    public static R success(Object... obj) {
        List<Object> list = Arrays.asList(obj);
        return new R("200", "OK", list);
    }

    // 需要返回状态
    public static R error(String code, String msg) {
        return new R(code, msg, null);
    }

    // 不需要返回状态
    public static R error() {
        return new R(Constants.CODE_500, "系统错误", null);
    }

    //返回分页
    public static R page(long total, Object data) {
        List<Object> list = Arrays.asList(total, data);
        return new R("200", total, data);
    }
}
