package com.maoyan.quickdevelop.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author maoyan
 * @date 2021/12/30 15:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DqSectionType", description = "增加和修改板块类型")
public class DqSectionTypeVO {
  private static final long serialVersionUID = 1L;
  /**
   * 版块分类名称
   */
  @NotNull
  private String sectionTypeName;
  /**
   * 所属版块ID
   */
  @NotNull
  private Long sectionId;
  /**
   * 类型权重
   */
  private Long sectionTypeWeight;
  /**
   * 分类类型(0-普通分类，1-特殊分类)
   */
  private String sectionTypeMold;
  /**
   * 分类网址
   */
  private String sectionTypeNetwork;
}
