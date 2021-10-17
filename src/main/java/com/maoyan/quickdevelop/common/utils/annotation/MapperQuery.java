package com.maoyan.quickdevelop.common.utils.annotation;

import com.maoyan.quickdevelop.common.utils.annotation.type.QueryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//要添加@Retention(RetentionPolicy.RUNTIME)才能被反射读取
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface MapperQuery {
    /**
     * 判断查询类型
     * @author 猫颜 
     * @date  上午10:03
     * @return com.maoyan.quickdevelop.common.utils.annotation.type.QueryType
     */
    QueryType queryType();
}
