package com.maoyan.quickdevelop.common.annotation;

import com.maoyan.quickdevelop.common.enums.BusinessType;

import java.lang.annotation.*;

/***
* @Author: 猫颜
* @Description: 自定义操作日志记录注解
* @DateTime: 下午4:37 2021/7/27
*/
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
