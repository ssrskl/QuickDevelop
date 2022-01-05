package com.maoyan.quickdevelop.common.core.domain.postprocessor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maoyan.quickdevelop.common.core.domain.DqSectionType;
import com.maoyan.quickdevelop.common.core.domain.DqTag;
import com.maoyan.quickdevelop.common.core.domain.DqType;
import com.maoyan.quickdevelop.common.core.domain.DqUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021年08月01日 上午7:08
 * 查询特供增强器
 * 查询不看注解，只看驼峰的命名，否则查询不了
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DqArticlePostProcesser implements Serializable {
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
     * 文章内容
     **/
    @TableField(value = "article_content")
    private String articleContent;

    /**
     * 文章首图（后续会优化更新）
     **/
    @TableField(value = "article_image")
    private String articleImage;

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
     * 文章状态(1为正常，0为被封禁)
     **/
    @TableField(value = "status")
    private String status;

    /**
     * 文章权重
     **/
    @TableField(value = "article_weight")
    private Long articleWeight;

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
    // private String articleSortWay;
    //---------数量相关-----------------
    private Long commentNum;
    private Long collectionNum;
    //--------增强内容------------------
    private DqUserPostProcessor dqArticleAuthor;
    private DqSectionType dqSectionType;
}