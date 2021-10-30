package com.maoyan.quickdevelop.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dq_user_role")
public class DqUserRole implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * 主键
   */
  @TableId(value = "user_role_id")
  private Long userRoleId;
  /**
   * 角色名称
   */
  @TableField(value = "role_name")
  private String roleName;
  /**
   * 用户ID
   */
  @TableField(value = "user_id")
  private Long userId;
  /**
   * 角色状态
   */
  @TableField(value = "role_status")
  private String roleStatus;
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
}
