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
 * @date 2021 年 12 月 03 日 上午7:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DqArticleQueryVO", description = "查询文章所用VO")
public class DqArticleQueryVO {
  private static final long serialVersionUID = 1L;

  /**
   * 文章ID
   **/
  @TableId(value = "article_id")
  private Long articleId;

  /**
   * 文章标题
   **/
  @TableField(value = "article_title")
  private String articleTitle;

  /**
   * 文章内容(暂定)
   **/
  @TableField(value = "article_content")
  private String articleContent;

  /**
   * 所属版块ID
   */
  @TableField(value = "section_id")
  private Long sectionId;

  @TableField(value = "section_type_id")
  private Long sectionTypeId;
  /**
   * 作者ID
   **/
  @TableField(value = "author_id")
  private Long authorId;

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
  //---------排序方式-----------------
  /**
   * 什么都不添则默认排序
   * 1-按照热度（评论数量排序）
   */
  private String articleSortWay;
}
