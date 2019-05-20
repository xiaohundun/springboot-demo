package com.example.commons.utils;
/*
 * chou created at 2019-03-27
 * @Description:
 * */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperUtil {
    private static final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();

    //  ObjectMapper初始化配置
    {
        //  配置反序列化时忽略json中有但是java中没有的字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //  配置序列化时不以TIMESTAMPS形式序列化
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //  在序列化时忽略值为NULL的属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //  配置使用字段默认值
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.USE_DEFAULTS);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
