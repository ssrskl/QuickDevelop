package com.maoyan.quickdevelop.common.core.domain.postprocessor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DqMessagePostProcesser implements Serializable {
  private static final long serialVersionUID = 1L;
  @TableId(value = "message_id")
  private Long messageId;

  /**
   * ========================接受消息用户的信息==========================
   **/
  @TableField(value = "re_user_id")
  private Long reUserId;
  /**
   * reMsg是指receive message(接收消息)
   **/
  private String reMsgUserName;
  private String reMsgNickName;
  private String reMsgEmail;
  private String reMsgAvatar;
  /**
   * ========================接受消息用户的信息==========================
   **/

  @TableField(value = "message_type")
  private String messageType;

  /**
   * ========================发送消息用户的信息==========================
   **/
  @TableField(value = "se_user_id")
  private Long seUserId;
  /**
   * seMsg是指send message(发送消息)
   **/
  private String seMsgUserName;
  private String seMsgNickName;
  private String seMsgEmail;
  private String seMsgAvatar;
  /**========================发送消息用户的信息==========================**/

  /**
   * ========================消息文章的信息==========================
   **/
  @TableField(value = "message_article_id")
  private Long messageArticleId;
  /**========================消息文章的信息==========================**/

  /**
   * ========================消息评论的信息==========================
   **/
  @TableField(value = "message_comment_id")
  private Long messageCommentId;
  /**
   * ========================消息评论的信息==========================
   **/

  @TableField(value = "message_is_read")
  private String messageIsRead;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;
}
