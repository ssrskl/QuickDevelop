package com.maoyan.quickdevelop.common.core.domain.postprocessor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maoyan.quickdevelop.common.core.domain.DqUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 21:28
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DqUserPostProcessor implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 用户ID */
  /**
   * 主键使用TableId注解，否则MybatisPlus默认使用id来查询
   * (不可修改)
   */
  @TableId(value = "user_id")
  private Long userId;

  /**
   * 用户账号（可修改，不能重复）
   */
  @TableField(value = "user_name")
  @NotNull
  private String userName;


  /**
   * 用户邮箱 （可修改，不能重复）
   */
  @Email(message = "邮箱格式不正确")
  @TableField(value = "email")
  @NotNull
  private String email;

  /**
   * 手机号码 （可修改，不能重复）
   */
  @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
  @TableField(value = "phone_number")
  private String phoneNumber;

  /**
   * 用户性别 0=男,1=女,2=未知（可修改）
   */
  @TableField(value = "sex")
  private String sex;

  /**
   * 用户头像 （可修改）
   */
  @TableField(value = "avatar")
  private String avatar;

//  /**
//   * 密码（可修改）
//   */
//  @TableField(value = "password")
//  @NotNull
//  private String password;

  /**
   * 帐号状态（1正常 0停用）（管理员可修改）
   */
  @TableField(value = "status")
  private String status;


  /**
   * 最后登录IP
   */
  @TableField(value = "loginIp")
  private String loginIp;

  /**
   * 最后登录时间
   */
  @TableField(value = "loginDate")
  private Date loginDate;

  /**
   * 个性签名（可修改）
   **/
  @TableField(value = "signature")
  private String signature;

  /**
   * 用户等级
   */
  @TableField(value = "grade")
  private Long grade;
  /**
   * 用户经验值
   */
  @TableField(value = "experience")
  private Long experience;


  /**
   *     check_param   varchar(255) comment '邮箱校验的参数（也可以当作盐值加密的参数）',
   *     check_status  char(1) comment '邮箱校验的状态(1-通过，0-未通过)',
   */
  /**
   * 邮箱校验的参数（也可以当作盐值加密的参数）
   */
//  @TableField(value = "check_param")
//  private String checkParam;
  /**
   * 邮箱校验的状态(1-通过，0-未通过)'
   */
  @TableField(value = "check_status")
  private String checkStatus;

  /**
   * 用户所在学校的ID
   */
  @TableField(value = "school_id")
  private Long schoolId;
  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  /**
   * 更新时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

  //---------增强内容------------------
  private List<DqUserRole> dqUserRoleList;
}
