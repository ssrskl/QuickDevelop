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
 * @date 2021 年 12 月 04 日 下午4:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DqSchoolUpdateVO", description = "修改学校的参数")
public class DqSchoolUpdateVO {

  private static final long serialVersionUID = 1L;

  @NotNull
  private Long schoolId;
  /**
   * 学校名称
   */
  private String schoolName;
  /**
   * 学校介绍
   */
  private String schoolIntroduce;
  /**
   * 学校校徽
   */
  private String schoolBadge;
  /**
   * 学校校训
   */
  private String schoolMotto;
  /**
   * 学校背景图
   */
  private String schoolBackground;
  /**
   * 学校建校时间
   */
  private Date schoolBuildDate;
  /**
   * 学校地理位置
   */
  private String schoolLocation;
}
