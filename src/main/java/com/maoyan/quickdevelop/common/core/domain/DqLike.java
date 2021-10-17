package com.maoyan.quickdevelop.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 猫颜
 * @date 2021年07月24日 下午3:21
 * 类的作用：TODO 点赞实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dq_like")
public class DqLike {
    /**
     * 点赞主键
     **/
    @TableId(value = "like_id")
    private Long likeId;
    /**
     * 点赞的用户ID
     **/
    @TableField(value = "give_like_user_id")
    private Long giveLikeUserId;
    /**
     * 被点赞的文章ID
     **/
    @TableField(value = "like_article_id")
    private Long likeArticleId;
    /**
     * 点赞状态（0为取消 1为点赞）
     **/
    @TableField(value = "status")
    private String status;
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