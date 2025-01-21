package com.xuecheng.content.api;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CourseBaseInfoController
 * @Description TODO
 * @Author huangXuRui
 * @Date 2025/1/21 11:34
 */
@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
@RestController
public class CourseBaseInfoController {


    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")                       // required = false 意思是设置此参数为非必填
    public PageResult<CourseBase> list(PageParams params, @RequestBody(required = false) QueryCourseParamsDto dto) {
        CourseBase courseBase = new CourseBase();
        courseBase.setName("999");
        courseBase.setCreateDate(LocalDateTime.now());
        List<CourseBase> courseBases = new ArrayList<>();
        courseBases.add(courseBase);
        PageResult<CourseBase> courseBasePageResult = new PageResult<CourseBase>(courseBases, 200, 5, 10);
        return courseBasePageResult;
    }
}
