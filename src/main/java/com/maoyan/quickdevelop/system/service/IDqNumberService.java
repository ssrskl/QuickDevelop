package com.maoyan.quickdevelop.system.service;

import com.maoyan.quickdevelop.common.core.domain.DqComment;

/**
 * TODO dqclass的相关数量查询
 *
 * @param
 * @author 猫颜
 * @date 2021/7/24 上午8:29
 * @return null
 */
public interface IDqNumberService {
    /**
     * 查询所有文章的数量
     *
     * @return int
     * @author 猫颜
     * @date 2021 7 24 上午8:32
     */
    public int allDqArticleNumber();

    /**
     * 查询指定文章下所有评论和回复的数量
     *
     * @return int
     * @author 猫颜
     * @date 2021 7 24 上午8:32
     */
    public int allCommentNumberInDqArticleById(Long dqArticleId);

    /**
     * 查询在指定根评论下的回复的数量
     *
     * @return int
     * @author 猫颜
     * @date 2021 7 24 上午8:33
     */
    public int allSonCommentNumInDqCommentByRootId(Long rootId);

    /***
     * @Author: 猫颜
     * @Description: 获取所有类型的数量
     * @DateTime: 上午8:49 2021/7/24
     * @Params:
     * @Return
     * @return int
     */
    public int allTypeNumber();

    /***
     * @Author: 猫颜
     * @Description: 获取所有用户的数量
     * @DateTime: 上午8:49 2021/7/24
     * @Params:
     * @Return
     * @return int
     */
    public int allUsersNum();

    /***
     * @Author: 猫颜
     * @Description: 根据作者的ID查询他的文章数量
     * @DateTime: 下午4:35 2021/7/31
     * @Params:
     * @param dqUserId
     * @Return
     * @return int
     */
    public int dqArticleNumByAuthorId(Long dqUserId);
    /**  获取指定id用户的评论数量 **/
    public int dqCommentNumByAuthorId(Long dqUserId);

    /** 获取当前用户的文章数量 **/
    public int dqArticleNumByNowDqUser();

    /** 获取当前用户评论的数量 **/
    public int dqCommentNumByNowDqUser();

}
