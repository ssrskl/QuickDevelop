package com.maoyan.quickdevelop.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 猫颜
 * @date 2021/12/2
 * 注册方法的用户入参
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DqUserVO {
  private static final long serialVersionUID = 1L;
  private String userName;
  @Email(message = "邮箱格式不正确")
  private String email;
  @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
  private String phoneNumber;
  private String sex;
  private String avatar;
  private String password;
  private String signature;
}
