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
@TableName(value = "dq_collection")
public class DqCollection implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 收藏ID
   */
  @TableId(value = "collection_id")
  private Long collectionId;
  /**
   * 发起收藏的用户ID
   */
  @TableField(value = "user_id")
  private Long userId;
  /**
   * 被收藏的文章ID
   */
  @TableField(value = "article_id")
  private Long articleId;

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
