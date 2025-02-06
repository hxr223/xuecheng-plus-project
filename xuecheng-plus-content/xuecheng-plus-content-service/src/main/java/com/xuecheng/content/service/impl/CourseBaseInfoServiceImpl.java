package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.DocFlavor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName CourseBaseInfoServiceImpl
 * @Description TODO
 * @Author huangXuRui
 * @Date 2025/1/23 14:23
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {
        // 拼装查询条件
        LambdaQueryWrapper<CourseBase> querywapper = new LambdaQueryWrapper<>();
        // 根据名称模糊查询
        querywapper.like(StringUtils.isNotEmpty(courseParamsDto.getCourseName()), CourseBase::getName, courseParamsDto.getCourseName());
        // 根据课程审核状态查询
        querywapper.eq(StringUtils.isNotEmpty(courseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, courseParamsDto.getAuditStatus());
        // 根据课程发布状态查询
        querywapper.eq(StringUtils.isNotEmpty(courseParamsDto.getPublishStatus()), CourseBase::getStatus, courseParamsDto.getPublishStatus());
        // 创建分页参数对象
        Page<CourseBase> courseBasePage = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 进行分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(courseBasePage, querywapper);
        // 数据列表
        List<CourseBase> records = pageResult.getRecords();
        // 总记录数
        long total = pageResult.getTotal();
        // 封装返回结果
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(records, total, pageParams.getPageNo(), pageParams.getPageSize());

        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto) {

//        // 参数的合法性校验
//        if (StringUtils.isBlank(addCourseDto.getName())) {
////            throw new RuntimeException("课程名称为空");
//            XueChengPlusException.cast("课程名称为空");
//        }
//
//        if (StringUtils.isBlank(addCourseDto.getMt())) {
//            XueChengPlusException.cast("课程分类为空");
//        }
//
//        if (StringUtils.isBlank(addCourseDto.getSt())) {
//            XueChengPlusException.cast("课程分类为空");
//        }
//
//        if (StringUtils.isBlank(addCourseDto.getGrade())) {
//            XueChengPlusException.cast("课程等级为空");
//        }
//
//        if (StringUtils.isBlank(addCourseDto.getTeachmode())) {
//            XueChengPlusException.cast("教育模式为空");
//        }
//
//        if (StringUtils.isBlank(addCourseDto.getUsers())) {
//            XueChengPlusException.cast("适应人群为空");
//        }
//
//        if (StringUtils.isBlank(addCourseDto.getCharge())) {
//            XueChengPlusException.cast("收费规则为空");
//        }
        // 向课程基本信息表course_base写入数据
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(addCourseDto, courseBase); // 只要属性名称一致就可以拷贝
        courseBase.setCompanyId(companyId);
        courseBase.setCreateDate(LocalDateTime.now());
        // 审核状态默认为未提交
        courseBase.setAuditStatus("202002");
        // 发布状态为未发布
        courseBase.setStatus("203001");
        // 插入数据库
        int insert = courseBaseMapper.insert(courseBase);
        if (insert <= 0) {
            XueChengPlusException.cast("添加课程失败");
        }

        // 向课程营销系course_market写入数据
        CourseMarket courseMarket = new CourseMarket();
        // 将页面输入的数据拷贝到courseMarket
        BeanUtils.copyProperties(addCourseDto, courseMarket);
        // 课程id
        Long id = courseBase.getId();
        courseMarket.setId(id);
        // 保存营销信息
        saveCourseMarket(courseMarket);
        // 从数据库查询课程详细信息, 包括两部分
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(id);

        return courseBaseInfo;
    }

    // 查询课程信息
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
        // 从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        // 从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        // 组装在一起
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        }
        // 通过courseCategoryMapper查询分类信息, 将分类名称放在courseBaseInfoDto对象中
        // todo: 课程分类的名称设置到courseBaseInfDto

        return courseBaseInfoDto;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {

        //课程id
        Long courseId = dto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase==null){
            XueChengPlusException.cast("课程不存在");
        }

        //校验本机构只能修改本机构的课程
        if(!courseBase.getCompanyId().equals(companyId)){
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }

        //封装基本信息的数据
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setChangeDate(LocalDateTime.now());

        //更新课程基本信息
        int i = courseBaseMapper.updateById(courseBase);

        //封装营销信息的数据
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        saveCourseMarket(courseMarket);
        //查询课程信息
        CourseBaseInfoDto courseBaseInfo = this.getCourseBaseInfo(courseId);
        return courseBaseInfo;

    }



    // 单独写一个方法保存营销信息, 逻辑: 存在则更新, 不存在则添加
    private int saveCourseMarket(CourseMarket courseMarket) {
        // 参数合法性校验
        String charge = courseMarket.getCharge();
        if (StringUtils.isEmpty(charge)) {
            XueChengPlusException.cast("收费规则为空");
        }
        // 如果课程收费, 价格没有填写也需要抛出异常
        if (charge.equals("201001")) {
            if (courseMarket.getPrice() == null || courseMarket.getPrice().floatValue() <= 0) {
                XueChengPlusException.cast("课程的价格不能为空并且必须大于0");
            }
        }
        // 从数据库中查询营销信息, 存在则更新, 不存在则添加
        Long id = courseMarket.getId();
        CourseMarket courseMarketOld = courseMarketMapper.selectById(id);
        if (courseMarketOld == null) {
            // 不存在走新增
            int insert = courseMarketMapper.insert(courseMarket);
            return insert;
        }else {
            // 存在走更新
            BeanUtils.copyProperties(courseMarket, courseMarketOld);
            courseMarketOld.setId(courseMarket.getId());
            // 更新
            int count = courseMarketMapper.updateById(courseMarketOld);
            return count;
        }


    }

}
