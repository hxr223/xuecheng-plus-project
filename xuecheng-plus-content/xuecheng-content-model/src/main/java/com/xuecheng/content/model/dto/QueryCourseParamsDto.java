package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName QueryCourseParamsDto
 * @Description 课程查询条件模型类
 * @Author huangXuRui
 * @Date 2025/1/21 10:56
 */
@Data
@ToString
public class QueryCourseParamsDto {

    // 审核状态
    private String auditStatus;
    // 课程名称
    private String courseName;
    // 发布状态
    private String publishStatus;

}
