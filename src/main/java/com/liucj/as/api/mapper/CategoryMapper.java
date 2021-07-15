package com.liucj.as.api.mapper;

import com.liucj.as.api.entity.CategoryEntity;
import com.liucj.as.api.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    List<CategoryEntity> getCategoryList();
    void addCategory(String categoryName,String createTime);
    void removeCategory(String categoryId);

}
