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

    private String debugInfo;

    public NormalException() {
        super();
    }

    /**
     * @param msg
     * @return
     * @Description checkCheckQuestion
     * @author hasdsd
     * @Date 2023/4/23
     */
    public NormalException(String msg) {
        super(msg);
        this.Message = msg;// 这句不加不行
    }

    /**
     * @param code,msg
     * @return
     * @Description 抛出错误信息
     * @author hasdsd
     * @Date 2023/4/23
     */
    //msg只在前台展示
    public NormalException(Integer code, String msg) {
        super();
        this.code = code;
        this.Message = msg;
    }

    //错误信息只在后台展示

    /**
     * @param msg, e
     * @return
     * @Description 抛出错误信息
     * @author hasdsd
     * @Date 2023/4/23
     */
    public NormalException(String msg, Exception e) {
        super();
        this.Message = msg;
        this.debugInfo = e.toString();
    }

    /**
     * @param code, msg, e
     * @return
     * @Description 抛出错误信息
     * @author hasdsd
     * @Date 2023/4/23
     */
    public NormalException(Integer code, String msg, Exception e) {
        super();
        this.code = code;
        this.Message = msg;
        this.debugInfo = e.toString();
    }
}
