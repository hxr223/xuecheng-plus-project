package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

public interface CourseBaseInfoService {

    // 课程分页查询
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto);

    /**
     * 新增课程
     * @param addCourseDto
     * @return
     */
    public CourseBaseInfoDto createCourseBase(Long companyId ,AddCourseDto addCourseDto);

    /**
     * @description 根据id查询课程基本信息
     * @param courseId  课程id
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author Mr.M
     * @date 2022/10/9 8:13
     */
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * @description 修改课程信息
     * @param companyId  机构id
     * @param dto  课程信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author Mr.M
     * @date 2022/9/8 21:04
     */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);


}
