package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @ClassName CourseCategoryService
 * @Description TODO
 * @Author huangXuRui
 * @Date 2025/1/24 13:03
 */
public interface CourseCategoryService {

    /**
     * 课程分类树形结构查询
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
