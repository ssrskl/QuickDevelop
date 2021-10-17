package com.maoyan.quickdevelop.system.domain.admin;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 猫颜
 * @date 2021年07月17日 下午9:03
 * 类的作用：TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "AdminDqUserVO")
public class AdminDqUserVO {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phonenumber;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 只有管理员可以更改
     * 用户状态
     */
    private String status;

    /**
     * 只有管理员可以更改
     * 用户权限
     */
    private String role;
}