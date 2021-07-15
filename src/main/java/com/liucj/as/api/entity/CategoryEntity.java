package com.liucj.as.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)//不展示为空的字段
//@JsonIgnoreProperties(value = {""}) //不展示哪个字段
public class CategoryEntity {

    public int categoryId ;
    public String categoryName ;
    public String createTime ;

}
