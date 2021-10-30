package com.maoyan.quickdevelop.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author 猫颜
 * @date 2021/5/28 11:24
 *用户登录对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "com.maoyan.quickdevelop.system.domain.vo.LoginVO", description = "用户登陆参数")
public class LoginVO {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "邮箱")
    @NonNull
    private String email;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "密码")
    @NonNull
    private String password;

}
