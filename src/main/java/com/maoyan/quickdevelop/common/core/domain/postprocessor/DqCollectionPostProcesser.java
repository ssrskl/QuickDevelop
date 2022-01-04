package com.maoyan.quickdevelop.common.core.domain.postprocessor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maoyan.quickdevelop.common.core.domain.DqSectionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 猫颜
 * @date 2021年08月01日 上午7:08
 * 查询特供增强器
 * 查询不看注解，只看驼峰的命名，否则查询不了
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DqCollectionPostProcesser implements Serializable {
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

    //------------增强--------------
    // 关于展示用户，只需要展示收藏内容，不太需要展示收藏者
    private DqArticlePostProcesser dqArticle;
}