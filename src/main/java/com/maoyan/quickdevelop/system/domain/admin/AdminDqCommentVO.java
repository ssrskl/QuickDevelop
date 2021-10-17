package com.maoyan.quickdevelop.system.domain.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 猫颜
 * @date 2021/5/28 15:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDqCommentVO {
    /** 评论的内容 **/
    private String content;

    /** 发表评论用户的Id **/
    private Long commentuserid;

    /** 文章的ID **/
    private Long articleid;

    /** 评论状态(0为正常，1为被封禁) **/
    private String status;

    /** 发表评论用户的昵称 **/
    private String commentusernickname;

    /** 发表评论的用户的用户名 **/
    private String commentusername;

    /** 发表评论的用户的头像 **/
    private String commentuseravatar;

//    /** 创建时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createTime;

    /** 评论的类型（1-评论 2-回复） **/
    private String commenttype;

    /** 被回复的人的ID **/
    private Long touserid;

    /** 被回复的人的用户名 **/
    private String tousername;

    /** 被回复的人的头像 **/
    private String touseravatar;

    /** 回复的评论的ID **/
    private Long replyid;


}
