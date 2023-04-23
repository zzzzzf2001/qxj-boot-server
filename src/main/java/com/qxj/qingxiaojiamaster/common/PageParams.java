package com.qxj.qingxiaojiamaster.common;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/22 23:41
 **/


import lombok.Data;
import lombok.ToString;

/**
 *
 * 分页查询的参数
 *
 *
 * **/



@Data
@ToString
public class PageParams {

    //页码
    private int CurrentPage=1;
    //每页显示记录数
    private int PageSize=10;

    public PageParams(){}

    public PageParams(int  currentPage, int pageSize) {
        CurrentPage = currentPage;
        PageSize = pageSize;
    }
}