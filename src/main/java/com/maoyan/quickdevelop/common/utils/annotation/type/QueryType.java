package com.maoyan.quickdevelop.common.utils.annotation.type;

/**
 * TODO 定义查询的类型
 * 暂时只适用于单参数查询
 * @author 猫颜 
 * @date  上午9:55
 * @param null 
 * @return null
 */
public enum QueryType {
    //并且(默认为and连接)
    AND,

    //或
    OR,

    //等于
    EQ,

    //不等于
    NE,

    //大于
    GT,

    //大于等于
    GE,

    //小于
    LT,

    //小于等于
    LE,

    //存在
    LIKE,

    //不存在
    NOTLIKE
}
