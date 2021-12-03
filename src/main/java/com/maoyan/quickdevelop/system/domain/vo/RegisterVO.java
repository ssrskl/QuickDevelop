package com.maoyan.quickdevelop.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author 猫颜
 * @date 2021/5/27 22:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "com.maoyan.quickdevelop.system.domain.vo.RegisterVO", description = "用户注册参数")
public class RegisterVO implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * 前端参数要与属性名相同
   * 例如用户名要传username，不能userName
   * 后面将会更新
   */
  /**
   * 用户账户
   **/

  @NonNull
  @ApiModelProperty(value = "用户名")
  private String userName;

  /**
   * 密码
   **/
  @NotNull
  @Size(min = 6, message = "密码不能低于六位")
  @ApiModelProperty(value = "密码")
  private String password;

  /**
   * 邮箱
   **/
  @NotNull
  @Email(message = "邮箱格式不正确")
  @ApiModelProperty(value = "邮箱")
  private String email;
  /**
   * 头像
   **/
  @ApiModelProperty(value = "头像")
  private String avatar;
  /**
   * 手机号
   */
  @ApiModelProperty(value = "手机号")
  @NotNull(message = "手机号不能为空")
  @NotBlank(message = "手机号不能为空")
  @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
  private String phoneNumber;
  private String sex;
  private String signature;
}
