package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: xuecheng-plus-project
 * @description: 修改课程dto
 * @author: hxr
 * @create: 2025-02-06 13:41
 **/

@Data
@ApiModel(value="EditCourseDto", description="修改课程基本信息")
public class EditCourseDto extends AddCourseDto{


    @ApiModelProperty(value = "课程id", required = true)
    private Long id;

}
