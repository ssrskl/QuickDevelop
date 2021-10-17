package com.maoyan.quickdevelop.system.service.admin;

import com.maoyan.quickdevelop.common.core.domain.DqComment;

import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:26
 * 评论
 */

public interface AdminDqCommentService {

    /**
     * 根据ID查询评论
     * @param dqCommentId
     * @return
     */
    public DqComment selectDqCommentById(Long dqCommentId);

    /**
     * 根据评论用户ID查找评论
     * @param dqUserId
     * @return
     */
    public List<DqComment> selectDqCommentByDqUserId(int pageNum, int pageSize,Long dqUserId);

    /**
     * 根据评论用户昵称查找评论(待优化)
     * @param dqUsername
     * @return
     */
    public List<DqComment> selectDqCommentByDqUsername(int pageNum, int pageSize,String dqUsername);

    /**
     * 根据文章ID查找评论
     * @param dqArticleId
     * @return
     */
    public List<DqComment> selectDqCommentByDqArticleId(int pageNum, int pageSize,Long dqArticleId);

    /**
     * 查询给用户的评论或回复通过用户ID
     * @param pageNum
     * @param pageSize
     * @param toUserId
     * @return
     */
    public List<DqComment> selectDqCommentToUserByToUserId(int pageNum, int pageSize,Long toUserId);

    /**
     * 查询给当前用户的的评论或回复
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<DqComment> selectDqCommentToMe(int pageNum, int pageSize);

    /**
     * 添加一个评论
     * @param dqComment
     * @return
     */
    public int insertDqComment(DqComment dqComment);

    /**
     * 获取全部的评论
     * @param pageNum 查询的页数
     * @param pageSize 每页的大小
     * @return
     */
    public List<DqComment> selectAllDqComments(int pageNum, int pageSize);

    /**
     * 根据Id删除评论
     * @param dqCommentId
     * @return
     */
    public int deleteDqCommentById(Long dqCommentId);


    /**
     * 根据ID更改评论
     * @param dqCommentId
     * @param dqComment
     * @return
     */
    public int updateDqCommentById(Long dqCommentId, DqComment dqComment);

}
