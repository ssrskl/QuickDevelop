package com.maoyan.quickdevelop.common.core.domain.postprocessor;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**========================类型信息==========================**/
//    private DqType dqType;
    /** 文章类别ID **/
    @TableField(value = "type_id")
    private Long typeId;
    @TableField(value = "type_name")
    private String typeName;
    @TableField(value = "type_image")
    private String typeImage;
    /**========================类型信息end==========================**/

    /**========================作者信息==========================**/
//    private DqUser dqUser;
    /** 作者ID **/
    @TableField(value = "author_id")
    private Long authorId;

    /** 作者的昵称 **/
    @TableField(value = "author_nickname")
    private String authorNickname;

    /** 作者的用户名 **/
    @TableField(value = "author_username")
    private String authorUsername;
    /**作者id**/
    @TableField(value = "user_id")
    private Long userId;
    /**作者用户名**/
    @TableField(value = "user_name")
    private String userName;
    /**作者昵称**/
    @TableField(value = "nick_name")
    private String nickName;
    /**作者邮箱**/
    @TableField(value = "email")
    private String email;
    /**作者手机号**/
    @TableField(value = "phone_number")
    private String phoneNumber;
    /**作者头像**/
    @TableField(value = "avatar")
    private String avatar;
    /**========================作者信息end==========================**/

    /**========================评论数量==========================**/
    @TableField(value = "comment_num")
    private int commentNum;
    /**========================评论数量end==========================**/

    /** 文章的tag**/
    private List<DqTag> articleTags;
    /** 文章排序 **/
    @TableField(value = "article_sort")
    private Long articleSort;

}