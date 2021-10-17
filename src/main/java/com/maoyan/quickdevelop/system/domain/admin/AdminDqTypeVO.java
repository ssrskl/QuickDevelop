package com.maoyan.quickdevelop.system.domain.admin;

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

public class AdminDqTypeVO {

    /**
     * 类型名称
     */
    private String typename;

    /**
     * 类型图片
     */
    private String typeimage;

    /**
     * 类型状态
     * 管理员可以更改
     */
    private String status;

    /**
     * 创建人ID
     * 管理员可以更改
     */
    private String createmanid;

    /**
     * 更新人ID
     * 管理员可以更改
     */
    private String updateManId;
    /**
     * 类型介绍
     */
    private String introduce;

}
