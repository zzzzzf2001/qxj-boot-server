package com.qxj.qingxiaojiamaster.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/4/22 09:29
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {
    private String code;
    private String msg;
    private T data;

    // 不需要返回值
    public static R success() {
        return new R(Constants.CODE_200, "操作成功", null);
    }

    // 需要返回值
    public static R success(Object data) {
        return new R(Constants.CODE_200, "", data);
    }

    // 需要返回状态
    public static R error(String code, String msg) {
        return new R(code, msg, null);
    }

    // 不需要返回状态
    public static R error() {
        return new R(Constants.CODE_500, "系统错误", null);
    }
}
