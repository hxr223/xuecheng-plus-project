package com.xuecheng.content.model.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 课程分类
 * </p>
 *
 * @author itcast
 */
@Data
@TableName("course_category")
public class CourseCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类标签默认和名称一样
     */
    private String label;

    /**
     * 父结点id（第一级的父节点是0，自关联字段id）
     */
    private String parentid;

    /**
     * 是否显示
     */
    private Integer isShow;

    /**
     * 排序字段
     */
    private Integer orderby;

    /**
     * 是否叶子
     */
    private Integer isLeaf;


}