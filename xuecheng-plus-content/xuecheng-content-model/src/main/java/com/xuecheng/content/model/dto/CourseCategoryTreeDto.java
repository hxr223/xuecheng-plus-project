package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CourseCategoryTreeDto
 * @Description TODO
 * @Author huangXuRui
 * @Date 2025/1/24 10:06
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    // 子节点
    List<CourseCategoryTreeDto> childrenTreeNodes;
}
