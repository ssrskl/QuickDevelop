package com.maoyan.quickdevelop.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 猫颜
 * @date 2021/5/28 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DqTypeVO {

    /**
     * 类型名称
     */
    private String typename;

    /**
     * 类型图片
     */
    private String typeimage;

    /**
     * 类型介绍
     */
    private String introduce;

}
