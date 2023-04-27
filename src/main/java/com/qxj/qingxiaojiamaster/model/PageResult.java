package com.qxj.qingxiaojiamaster.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/4/27 22:57
 **/


@Data
@ToString
public class PageResult<T> implements Serializable {

    //数据列表
    private List<T> records;

    //总记录数
    private long counts;

    //每页记录数
    private  Integer pageSize;


    private Integer page;

    public PageResult(List<T> records, long counts, Integer page,Integer pageSize) {
        this.records = records;
        this.counts = counts;
        this.pageSize = pageSize;
        this.page=page;
    }

}