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
@TableName(value = "dq_section_type")
public class DqSectionType implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * 版块分类ID
   */
  @TableId(value = "section_type_id")
  private Long sectionTypeId;
  /**
   * 版块分类名称
   */
  @TableField(value = "section_type_name")
  private String sectionTypeName;
  /**
   * 所属版块ID
   */
  @TableField(value = "section_id")
  private Long sectionId;
  /**
   * 类型权重
   */
  @TableField(value = "section_type_weight")
  private Long sectionTypeWeight;
  /**
   * 分类类型
   */
  @TableField(value = "section_type_mold")
  private String sectionTypeMold;
  /**
   * 分类网址
   */
  @TableField(value = "section_type_network")
  private String sectionTypeNetwork;
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
