package com.liucj.as.api.service;

import com.liucj.as.api.entity.CategoryEntity;
import com.liucj.as.api.entity.UserEntity;
import com.liucj.as.api.mapper.CategoryMapper;
import com.liucj.as.api.mapper.UserMapper;
import com.liucj.as.api.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryEntity> categories() {
        return categoryMapper.getCategoryList();
    }

    public void addCategory(String categoryName) {
        categoryMapper.addCategory(categoryName, DateUtil.currentDate());
    }

    public void removeCategory(String categoryId) {
        categoryMapper.removeCategory(categoryId);
    }
}
