package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqComment;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqArticlePostProcesser;
import com.maoyan.quickdevelop.common.core.domain.postprocessor.DqCommentPostProcesser;
import com.maoyan.quickdevelop.system.domain.DqCommentVO;
import com.maoyan.quickdevelop.system.mapper.postprocessor.DqCommentPostProcessorMapper;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 猫颜
 * @date 2021/5/27 22:26
 * 评论
 */

public interface IDqCommentService {


    /**
     *  评论通用查询
     * @author 猫颜
     * @date  上午8:36
     * @param dqComment
     * @return com.maoyan.quickdevelop.common.core.domain.DqComment
     */
    public List<DqCommentPostProcesser> commonSelectDqCommentPostProcesser(int pageNum,int pageSize,DqComment dqComment);
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
//    public List<DqComment> selectDqCommentByDqUsername(int pageNum, int pageSize,String dqUsername);

    /**
     * 根据文章ID查找评论
     * @param dqArticleId
     * @return
     */
    public List<DqComment> selectDqCommentByDqArticleId(int pageNum, int pageSize,Long dqArticleId);

    /**
     * 根据根评论查询回复（根据id查询根评论下的所有回复）
     * @author 猫颜
     * @date  2021/7/24/上午7:44
     * @param pageNum
     * @param pageSize
     * @param rootId 
     * @return java.util.List<com.maoyan.quickdevelop.common.core.domain.DqComment>
     */
    public List<DqComment> selectDqCommentsByRootId(int pageNum, int pageSize,Long rootId);

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
     *发表一个评论
     * @param dqCommentVO
     * @return
     */
    public int publishDqComment(DqCommentVO dqCommentVO);

    /**
     * 根据Id删除评论
     * @param dqCommentId
     * @return
     */
    public int deleteDqCommentById(Long dqCommentId);

}
