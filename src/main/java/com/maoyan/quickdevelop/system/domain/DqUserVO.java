package com.maoyan.quickdevelop.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author 猫颜
 * @date 2021/5/28 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DqUserVO {

    /**
     * 用户ID
     */
    @NonNull
    private Long dquserid;
    /**
     * 用户名
     */
    private String username;
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

//    /**
//     * 用户状态
//     */
//    private String status;
//
//    /**
//     * 用户角色
//     */
//    private String role;

}
