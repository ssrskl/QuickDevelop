package com.maoyan.quickdevelop.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maoyan.quickdevelop.common.core.domain.dqabstract.DqStatusDispose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dq_follow_interdquser")
public class DqFollowUser implements Serializable {
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
  @TableField(value = "followed_dquser_id")
  private Long followedDqUserId;
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
