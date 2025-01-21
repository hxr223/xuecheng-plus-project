package com.xuecheng.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PageResult
 * @Description 分页查询结果模型类
 * @Author huangXuRui
 * @Date 2025/1/21 11:20
 */
@ToString
@Data
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    // 数据列表
    private List<T> items;

    // 总记录数
    private long counts;

    // 当前页码
    private long page;

    // 每页记录数
    private long pageSize;


}
