package com.qxj.qingxiaojiamaster.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/4/22 09:32
 **/

@Getter
@Setter
public class NormalException extends RuntimeException {
    //默认错误代码500
    private Integer code = 500;
    private String Message;

    public NormalException() {
        super();
    }

    public NormalException(String msg) {
        super(msg);
        this.Message = msg;// 这句不加不行
    }

    public NormalException(Integer code, String msg) {
        super();
        this.code = code;
        this.Message = msg;
    }
}
