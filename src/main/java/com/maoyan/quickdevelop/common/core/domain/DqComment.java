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

/**
 * @author 猫颜
 * @date 2021/5/27 22:04
 * 评论
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dq_comment")
public class DqComment extends DqStatusDispose implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 评论的ID
   **/
  @TableId(value = "comment_id")
  private Long commentId;

  /**
   * 文章的ID
   **/
  @TableField(value = "article_id")
  private Long articleId;

  /**
   * 评论的内容
   **/
  @TableField(value = "content")
  private String content;

  /**
   * 发表评论用户的Id
   **/
  @TableField(value = "comment_user_id")
  private Long commentUserId;

  /**
   * 评论状态(1为正常，0为被封禁)
   **/
  @TableField(value = "status")
  private String status;


  /**
   * 被回复的人的ID
   **/
  @TableField(value = "to_user_id")
  private Long toUserId;

  /**
   * 回复的评论的ID(0则为评论而不是回复)
   **/
  @TableField(value = "reply_id")
  private Long replyId;

  /**
   * 根评论ID
   **/
  @TableField(value = "root_id")
  private Long rootId;

  /**
   *删除标志
   **/
  @TableField(value = "delete_flag")
  private Long deleteFlag;
  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
}
