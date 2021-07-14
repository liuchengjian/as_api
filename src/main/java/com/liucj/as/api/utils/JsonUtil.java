package com.liucj.as.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static String toJsonString(Object o){
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(0);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static <T>T fromJson(String json,Class<T> tClass){
        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try {
            t = mapper.readValue(json,tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

}
