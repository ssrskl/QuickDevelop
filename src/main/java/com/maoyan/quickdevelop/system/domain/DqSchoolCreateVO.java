package com.maoyan.quickdevelop.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 猫颜
 * @date 2021 年 12 月 04 日 上午8:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DqSchoolCreateVO", description = "增加学校的参数")
public class DqSchoolCreateVO {

  private static final long serialVersionUID = 1L;
  /**
   * 学校名称
   */
  @TableField(value = "school_name")
  @NotNull
  private String schoolName;
  /**
   * 学校介绍
   */
  @TableField(value = "school_introduce")
  @NotNull
  private String schoolIntroduce;
  /**
   * 学校校徽
   */
  @TableField(value = "school_badge")
  @NotNull
  private String schoolBadge;
  /**
   * 学校校训
   */
  @TableField(value = "school_motto")
  @NotNull
  private String schoolMotto;
  /**
   * 学校背景图
   */
  @TableField(value = "school_background")
  @NotNull
  private String schoolBackground;
  /**
   * 学校建校时间
   */
  @TableField(value = "school_build_date")
  @NotNull
  private Date schoolBuildDate;
  /**
   * 学校地理位置
   */
  @TableField(value = "school_location")
  @NotNull
  private String schoolLocation;
}
