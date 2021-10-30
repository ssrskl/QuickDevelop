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
@TableName(value = "dq_section")
public class DqSection implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * 版块主键
   */
  @TableId(value = "section_id")
  private Long sectionId;
  /**
   * 版块名称
   */
  @TableField(value = "section_name")
  private String sectionName;
  /**
   * 版块介绍
   */
  @TableField(value = "section_introduce")
  private String sectionIntroduce;
  /**
   * 版块logo
   */
  @TableField(value = "section_logo")
  private String sectionLogo;
  /**
   * 版块背景图
   */
  @TableField(value = "section_background")
  private String sectionBackground;
  /**
   * 版主ID
   */
  @TableField(value = "section_admin_user_id")
  private Long sectionAdminUserId;
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
