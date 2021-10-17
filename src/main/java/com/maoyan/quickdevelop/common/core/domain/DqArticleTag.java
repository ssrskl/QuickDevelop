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

/**
 * @author 猫颜
 * @date 2021年07月28日 下午7:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dq_article_tag")
public class DqArticleTag implements Serializable {

    @TableId(value = "article_tag_id")
    private Long articleTagId;
    @TableField(value = "article_id")
    private Long articleId;
    @TableField(value = "tag_id")
    private Long tagId;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}