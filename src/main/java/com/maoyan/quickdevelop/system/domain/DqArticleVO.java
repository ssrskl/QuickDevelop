package com.maoyan.quickdevelop.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 猫颜
 * @date 2021/5/28 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DqArticleVO", description = "增加和修改文章参数")
public class DqArticleVO {
  /**
   * 文章标题
   **/
  @TableField(value = "article_title")
  @NotNull
  private String articleTitle;

  /**
   * 文章内容
   **/
  @TableField(value = "article_content")
  @NotNull
  private String articleContent;

  /**
   * 文章首图（后续会优化更新）
   **/
  @TableField(value = "article_image")
  private String articleImage;
  /**
   * 文章所属的类型
   */
  @TableField(value = "section_type_id")
  @NotNull
  private Long sectionTypeId;
}
