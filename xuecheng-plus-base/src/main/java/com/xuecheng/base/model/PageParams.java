package com.xuecheng.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName PageParams
 * @Description 分页查询参数
 * @Author huangXuRui
 * @Date 2025/1/21 10:48
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageParams {

    // 当前页码
    @ApiModelProperty("页码")
    private Long pageNo = 1L;

    // 每页显示记录数
    @ApiModelProperty("每页记录数")
    private Long pageSize = 30L;


}
