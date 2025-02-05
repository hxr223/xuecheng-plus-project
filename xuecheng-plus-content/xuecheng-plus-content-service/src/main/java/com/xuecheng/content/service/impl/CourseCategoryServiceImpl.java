package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName CourseCategoryServiceImpl
 * @Description TODO
 * @Author huangXuRui
 * @Date 2025/1/24 13:05
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {


    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {

        // 调用mapper递归查询出分类信息
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        // 找到每个节点的子节点，最终封装成List<CourseCategoryTreeDto>
        // 先将list转换为map, key就是节点的id, value就是CourseCategoryTreeDto对象, 目的就是为了方便从map获取节点
        Map<String, CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos.stream()
                .filter(item -> !id.equals(item.getId())) // 排除根节点
                .collect(Collectors.toMap(CourseCategoryTreeDto::getId, value -> value, (key1, key2) -> key2));
        // 定义一个List，作为最终返回的list
        List<CourseCategoryTreeDto> courseCategoryList = new ArrayList<>();
        // 从头遍历List<CourseCategoryTreeDto>, 一边遍历一边找子节点放在父节点的childrenTreeNodes
        courseCategoryTreeDtos.stream()
                .filter(item -> !id.equals(item.getId()))
                .forEach(item -> {
                    if (item.getParentid().equals(id)) {
                        courseCategoryList.add(item);
                    }
                    // 找到当前节点的父节点
                    CourseCategoryTreeDto parentCategory = mapTemp.get(item.getParentid());
                    if (null != parentCategory) {
                        if (parentCategory.getChildrenTreeNodes() == null) {
                            // 如果该父节点的ChildrenTreeNodes属性为空要new一个集合，因为要向该集合中放它的子节点
                            parentCategory.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto >());
                        }
                        // 把每个节点的子节点放在父节点的childrenTreeNodes属性中
                        parentCategory.getChildrenTreeNodes().add(item);
                    }

                });
        return courseCategoryList;
    }
}
