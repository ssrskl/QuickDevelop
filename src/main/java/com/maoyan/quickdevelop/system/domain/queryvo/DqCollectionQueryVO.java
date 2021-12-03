package com.maoyan.quickdevelop.system.domain.queryvo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 猫颜
 * @date 2021 年 12 月 03 日 上午7:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DqCollectionQueryVO", description = "查询收藏所用VO")
public class DqCollectionQueryVO {
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
