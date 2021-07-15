package com.liucj.as.api.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {
    /**
     * 处理分页数据
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> getPageData(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        Map<String, Object> data = new HashMap<>();
        data.put("total", pageInfo.getTotal());
        data.put("list", pageInfo.getList());
        return data;
    }

}
