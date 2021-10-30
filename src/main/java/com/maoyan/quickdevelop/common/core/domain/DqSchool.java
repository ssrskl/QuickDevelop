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
@TableName(value = "dq_school")
public class DqSchool implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * 学校Id
   */
  @TableId(value = "school_id")
  private Long schoolId;
  /**
   * 学校名称
   */
  @TableField(value = "school_name")
  private String schoolName;
  /**
   * 学校介绍
   */
  @TableField(value = "school_introduce")
  private String schoolIntroduce;
  /**
   * 学校校徽
   */
  @TableField(value = "school_badge")
  private String schoolBadge;
  /**
   * 学校校训
   */
  @TableField(value = "school_motto")
  private String schoolMotto;
  /**
   * 学校背景图
   */
  @TableField(value = "school_background")
  private String schoolBackground;
  /**
   * 学校建校时间
   */
  @TableField(value = "school_build_date")
  private Date schoolBuildDate;
  /**
   * 学校地理位置
   */
  @TableField(value = "school_location")
  private String schoolLocation;
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
