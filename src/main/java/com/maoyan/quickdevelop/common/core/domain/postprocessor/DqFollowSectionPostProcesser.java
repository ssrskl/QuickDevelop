package com.maoyan.quickdevelop.common.core.domain.postprocessor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maoyan.quickdevelop.common.core.domain.DqSection;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 猫颜
 * @date 2022/1/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DqFollowSectionPostProcesser implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * 主键
   */
  @TableId(value = "follow_id")
  private Long followId;
  /**
   * 发起关注的用户ID
   */
  @TableField(value = "give_follow_dquser_id")
  private Long giveFollowDqUserId;
  /**
   * 被关注的用户ID
   */
  @TableField(value = "followed_dqsection_id")
  private Long followedDqsectionId;
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

  /**
   * 增强
   */
  private DqUser followUser;
  private DqSectionPostProcessor followedSection;
}
