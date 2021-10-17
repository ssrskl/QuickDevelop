package com.maoyan.quickdevelop.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maoyan.quickdevelop.common.core.domain.dqabstract.DqStatusDispose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 猫颜
 * @date 2021/5/27 21:53
 * 文章
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dq_article")
public class DqArticle extends DqStatusDispose implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 文章ID **/
    @TableId(value = "article_id")
    private Long articleId;

    /** 文章标题 **/
    @TableField(value = "article_title")
    private String articleTitle;

    /** 文章内容 **/
    @TableField(value = "article_content")
    private String articleContent;

    /** 文章首图（后续会优化更新）**/
    @TableField(value = "article_image")
    private String articleImage;

    /** 文章状态(0为正常，1为被封禁) **/
    @TableField(value = "status")
    private String status;

    /** 文章类别ID **/
    @TableField(value = "type_id")
    private Long typeId;

    /** 作者ID **/
    @TableField(value = "author_id")
    private Long authorId;

    /** 作者的昵称 **/
    @TableField(value = "author_nickname")
    private String authorNickname;

    /** 作者的用户名 **/
    @TableField(value = "author_username")
    private String authorUsername;

    /** 文章排序 **/
    @TableField(value = "article_sort")
    private Long articleSort;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
